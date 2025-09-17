package com.example.books.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.ui.registration.mvi.RegistrationAction
import com.example.books.ui.registration.mvi.RegistrationSideEffect
import com.example.books.ui.registration.mvi.RegistrationState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class RegistrationViewModel : ViewModel(),
    ContainerHost<RegistrationState, RegistrationSideEffect> {

    override val container = container<RegistrationState, RegistrationSideEffect>(
        RegistrationState()
    )

    // TODO: UpdateLogin/Password Игорь посмотрит должен ли это быть отденый RegistrationAction.
    // TODO: Вся авторизация должны работать через соответствующий repository (AuthorizationRepository)
    // TODO: Можно ли сюда прикрутить токен. Разобраться как это работает

    // TODO: Ыsignup and registration объедлинить в пакет authorization

    private val auth: FirebaseAuth =
        FirebaseAuth.getInstance()//экземпляр FirebaseAuth — это объект, который управляет аутентификацией пользователей в Firebase

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        intent {
            reduce {
                state.copy(
                    isLoading = false,
                    errorMessage = throwable.message
                )
            }//Меняет состояние state то, что нужно отобразить на экране
            postSideEffect(RegistrationSideEffect.ShowError(throwable.message ?: "Ошибка"))
        }
    }

    fun dispatch(action: RegistrationAction) {
        when (action) {
            is RegistrationAction.UpdateLogin -> updateLogin(action.login)
            is RegistrationAction.UpdatePassword -> updatePassword(action.password)
            is RegistrationAction.SubmitLogin -> login()
            is RegistrationAction.SubmitRegister -> register()
        }
    }

    private fun updateLogin(login: String) = intent {
        reduce { state.copy(login = login) }
    }

    private fun updatePassword(password: String) = intent {
        reduce { state.copy(password = password) }
    }

    private fun login() = intent {
        if (state.isLoading) return@intent
        reduce { state.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(ceh) {
            auth.signInWithEmailAndPassword(state.login, state.password)
                .addOnCompleteListener { task ->
                    intent {
                        reduce { state.copy(isLoading = false) }//Меняет состояние state то, что нужно отобразить на экране
                        if (task.isSuccessful) {
                            postSideEffect(RegistrationSideEffect.NavigateToCatalog)
                        } else {
                            val message = task.exception?.message ?: "Ошибка входа"
                            reduce { state.copy(errorMessage = message) } //Меняет состояние state то, что нужно отобразить на экране
                            postSideEffect(RegistrationSideEffect.ShowError(message))
                        }
                    }
                }
        }
    }

    private fun register() = intent {
        if (state.isLoading) return@intent
        reduce { state.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(ceh) {
            auth.createUserWithEmailAndPassword(state.login, state.password)
                .addOnCompleteListener { task ->
                    intent {
                        reduce { state.copy(isLoading = false) }
                        if (task.isSuccessful) {
                            postSideEffect(RegistrationSideEffect.NavigateToCatalog)
                        } else {
                            val message = task.exception?.message ?: "Ошибка регистрации"
                            reduce { state.copy(errorMessage = message) }
                            postSideEffect(RegistrationSideEffect.ShowError(message))
                        }
                    }
                }
        }
    }
}


package com.example.books.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()//экземпляр FirebaseAuth — это объект, который управляет аутентификацией пользователей в Firebase

    // LiveData для отслеживания состояния загрузки
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    // LiveData для хранения логина
    private val _login = MutableLiveData("")
    val login: LiveData<String> = _login

    // LiveData для хранения пароля
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    // Обновление логина при вводе пользователя
    fun onLoginChange(newLogin: String) {
        _login.value = newLogin
    }
    // Обновляение пароля при вводе пользователя
    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    // Функция для входа пользователя с email и паролем
    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                // Вызов Firebase метода входа
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Если вход успешен вызываем функцию home для перехода в каталог
                            home()
                        } else {
                            _errorMessage.value = task.exception?.message ?: "Ошибка входа"
                        }
                    }
            } catch (ex: Exception) {
                _errorMessage.value = ex.message ?: "Ошибка входа"
            }
        }

    // Функция для создания нового пользователя с email и паролем
    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        //если пользователь нажал на кнопку зарегистрироваться, отправляется запрос на сервер
        //нужно заблокировать повторные запросы если пользователь несколько раз нажмет на кнопку
        if (_loading.value == true) return // Если уже идёт загрузка, ничего не делаем
        _loading.value = true // Устанавливаем загрузку в true, чтобы UI показывал "загрузка"
        _errorMessage.value = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loading.value = false
                if (task.isSuccessful) {
                    home()
                } else {
                    _errorMessage.value = task.exception?.message ?: "Ошибка регистрации"
                }
            }
    }
}


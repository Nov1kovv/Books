package com.example.books.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.LoadingState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _login = MutableLiveData("")
    val login: LiveData<String> = _login

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun onLoginChange(newLogin: String) {
        _login.value = newLogin
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("FB", "signInWithEmailAndPassword: SUCCESS")
                            home()
                        } else {
                            Log.d("FB", "signInWithEmailAndPassword: FAILED ${task.exception?.message}")
                            _errorMessage.value = task.exception?.message ?: "Ошибка входа"
                        }
                    }
            } catch (ex: Exception) {
                Log.d("FB", "signIn Exception: ${ex.message}")
                _errorMessage.value = ex.message ?: "Ошибка входа"
            }
        }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == true) return
        _loading.value = true
        _errorMessage.value = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loading.value = false
                if (task.isSuccessful) {
                    Log.d("FB", "createUserWithEmailAndPassword: SUCCESS")
                    home()
                } else {
                    Log.d("FB", "createUser FAILED: ${task.exception?.message}")
                    _errorMessage.value = task.exception?.message ?: "Ошибка регистрации"
                }
            }
    }
}


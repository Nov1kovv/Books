package com.example.books

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {

    private val _login = mutableStateOf("")
    val login: State<String> = _login

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun onLoginChange(newLogin: String) {
        _login.value = newLogin
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }
}
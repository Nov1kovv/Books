package com.example.books

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {

    private val _login = mutableStateOf("") //mutableStateOf позволяет Compose отслеживать изменения и перерисовывать UI при изменении значения.
    val login: State<String> = _login //неизменяемое состояние для доступа из UI

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun onLoginChange(newLogin: String) { // Метод, который вызывается из UI при изменении текста логина.
        _login.value = newLogin // Обновляет значение login, что вызывает перерисовку UI.
    }

    fun onPasswordChange(newPassword: String) { // Метод, который вызывается из UI при изменении текста пароля.
        _password.value = newPassword
    }
}
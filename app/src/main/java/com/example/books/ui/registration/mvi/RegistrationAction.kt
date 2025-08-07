package com.example.books.ui.registration.mvi

sealed class RegistrationAction {
    data class UpdateLogin(val login: String) : RegistrationAction()
    data class UpdatePassword(val password: String) : RegistrationAction()
    object SubmitLogin : RegistrationAction()
    object SubmitRegister : RegistrationAction()
}
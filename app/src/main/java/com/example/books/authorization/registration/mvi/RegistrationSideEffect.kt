package com.example.books.authorization.registration.mvi

sealed class RegistrationSideEffect {
    data class ShowError(val message: String) : RegistrationSideEffect()
    object NavigateToCatalog : RegistrationSideEffect()
}
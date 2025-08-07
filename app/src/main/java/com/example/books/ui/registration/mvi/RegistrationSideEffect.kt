package com.example.books.ui.registration.mvi

sealed class RegistrationSideEffect {
    data class ShowError(val message: String) : RegistrationSideEffect()
    object NavigateToCatalog : RegistrationSideEffect()
}
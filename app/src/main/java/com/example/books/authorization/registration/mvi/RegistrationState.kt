package com.example.books.authorization.registration.mvi

data class RegistrationState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToCatalog: Boolean = false
)
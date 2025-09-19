package com.example.domain.repository

interface AuthorizationRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun logout()
    fun getCurrentUserId(): String?
}
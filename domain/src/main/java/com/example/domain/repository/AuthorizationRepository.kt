package com.example.domain.repository

interface AuthorizationRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun signOut()
    fun getCurrentUserId(): String?
}
package com.example.data.data.repository

import com.example.domain.repository.AuthorizationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


class AuthorizationRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthorizationRepository {

    override suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid
}
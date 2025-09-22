package com.example.data.data.repository

import com.example.domain.model.FavoriteBook
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FavoriteRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FavoriteRepository {

    private val userId: String
        get() = auth.currentUser?.uid ?: "anonymous"

    override fun getFavorites(userId: String): Flow<List<FavoriteBook>> = callbackFlow {
        val subscription = firestore.collection("favorites")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val books = snapshot.documents.mapNotNull {
                        it.toObject(FavoriteBook::class.java)
                    }
                    trySend(books)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun addToFavorites(book: FavoriteBook) {
        val bookWithUser = book.copy(userId = userId)
        firestore.collection("favorites")
            .document("${userId}_${book.id}")
            .set(bookWithUser)
    }

    override suspend fun removeFromFavorites(bookId: String, userId: String) {
        firestore.collection("favorites")
            .document("${userId}_$bookId")
            .delete()
    }
}
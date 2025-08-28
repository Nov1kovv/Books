package com.example.books.ui.bottombar.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

    private val _favorites = MutableStateFlow<List<FavoriteBook>>(emptyList())
    val favorites: StateFlow<List<FavoriteBook>> = _favorites

    init {
        observeFavorites()
    }

   //Подписка на изменения в Firestore
    private fun observeFavorites() {
       firestore.collection("favorites")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val books = snapshot.documents.mapNotNull { it.toObject(FavoriteBook::class.java) }
                    _favorites.value = books
                }
            }
    }

    fun addToFavorites(book: FavoriteBook) {
        viewModelScope.launch {
            val bookWithUser = book.copy(userId = userId)
            firestore.collection("favorites")
                .document("${userId}_${book.id}")
                .set(bookWithUser)
        }
    }

    fun removeFromFavorites(bookId: String) {
        viewModelScope.launch {
            firestore.collection("favorites")
                .document("${userId}_$bookId")
                .delete()
        }
    }
}
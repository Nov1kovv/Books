package com.example.books.ui.bottombar.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FavoriteBook
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    val favorites: StateFlow<List<FavoriteBook>> =
        repository.getFavorites(auth.currentUser?.uid ?: "anonymous")
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addToFavorites(book: FavoriteBook) {
        viewModelScope.launch { repository.addToFavorites(book) }
    }

    fun removeFromFavorites(bookId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(
                bookId,
                auth.currentUser?.uid ?: "anonymous"
            )
        }
    }
}
package com.example.books.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.ui.bottombar.favorite.FavoriteBook
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.books.ui.catalog.BookCatalogViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val catalogViewModel: BookCatalogViewModel,
    private val favoriteViewModel: FavoriteViewModel
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state

    fun loadBook(bookId: String) {
        val book = catalogViewModel.getBookById(bookId)
        val isFavorite = favoriteViewModel.favorites.value.any { it.id == bookId }
        _state.value = BookDetailState(book, isFavorite)
    }

    fun toggleFavorite() {
        val currentBook = _state.value.book ?: return
        viewModelScope.launch {
            if (_state.value.isFavorite) {
                favoriteViewModel.removeFromFavorites(currentBook.id)
            } else {
                favoriteViewModel.addToFavorites(
                    FavoriteBook(
                        id = currentBook.id,
                        title = currentBook.title,
                        description = currentBook.description ?: "",
                        imageUrl = currentBook.imageUrl ?: "",
                        userId = ""
                    )
                )
            }
            _state.value = _state.value.copy(isFavorite = !_state.value.isFavorite)
        }
    }
}
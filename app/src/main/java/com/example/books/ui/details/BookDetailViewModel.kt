package com.example.books.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.ui.bottombar.favorite.FavoriteBook
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.books.ui.catalog.BookCatalogViewModel
import com.example.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val catalogViewModel: BookCatalogViewModel,
    private val favoriteViewModel: FavoriteViewModel,
    private val repository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state

    fun loadBook(bookId: String) {
        viewModelScope.launch {
            // сначала пробуем найти в каталоге
            val fromCatalog = catalogViewModel.getBookById(bookId)
            if (fromCatalog != null) {
                _state.value = BookDetailState(
                    book = fromCatalog,
                    isFavorite = favoriteViewModel.favorites.value.any { it.id == bookId }
                )
                return@launch
            }

            // если не нашли — пробуем взять из избранного
            val fromFav = favoriteViewModel.favorites.value.find { it.id == bookId }
            if (fromFav != null) {
                _state.value = BookDetailState(
                    book = com.example.domain.model.Book(
                        id = fromFav.id,
                        title = fromFav.title,
                        authors = emptyList(), // authors у FavoriteBook нет
                        description = fromFav.description,
                        imageUrl = fromFav.imageUrl
                    ),
                    isFavorite = true
                )
                return@launch
            }

            // если нет ни там, ни там — идём в сеть
            val fromApi = repository.getBookById(bookId)
            if (fromApi != null) {
                _state.value = BookDetailState(
                    book = fromApi,
                    isFavorite = favoriteViewModel.favorites.value.any { it.id == bookId }
                )
            } else {
                // книга не найдена даже в API
                _state.value = BookDetailState(book = null, isFavorite = false)
            }
        }
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
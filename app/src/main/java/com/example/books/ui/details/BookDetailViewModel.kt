package com.example.books.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FavoriteBook
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.domain.repository.BookRepository
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// TODO: Нельзя инжектить одну вьюмодель в другую
class BookDetailViewModel( // для доступа к списку книг из каталога
    private val favoriteRepository: FavoriteRepository,
    private val auth: FirebaseAuth, // для работы с избранными книгами
    private val repository: BookRepository // для загрузки книги по id из API
) : ViewModel() {

    // TODO: Shared/StateFlow знать на изусть
    private val _state = MutableStateFlow(BookDetailState()) // внутреннее состояние экрана
    val state: StateFlow<BookDetailState> = _state // публичное состояние для UI

    init { // Подписка на изменения избранного пользователя
        viewModelScope.launch {
            favoriteRepository.getFavorites(auth.currentUser?.uid ?: "anonymous")
                .collect { favs ->
                    val currentBook = _state.value.book
                    if (currentBook != null) {
                        val isFav = favs.any { it.id == currentBook.id }
                        _state.value = _state.value.copy(isFavorite = isFav)
                    }
                }
        }
    }

    // Загружает книгу по bookId
    fun loadBook(bookId: String) {
        viewModelScope.launch {
            val fromApi = repository.getBookById(bookId)
            if (fromApi != null) {
                _state.value = BookDetailState(
                    book = fromApi,
                    isFavorite = false // актуальное значение подтянется из collect
                )
            } else {
                _state.value = BookDetailState(book = null, isFavorite = false)
            }
        }
    }


    // Добавляет или удаляет текущую книгу из избранного
    fun toggleFavorite() {
        val currentBook = _state.value.book ?: return
        viewModelScope.launch {
            if (_state.value.isFavorite) {
                favoriteRepository.removeFromFavorites(
                    currentBook.id,
                    auth.currentUser?.uid ?: "anonymous"
                )
            } else {
                favoriteRepository.addToFavorites(
                    FavoriteBook(
                        id = currentBook.id,
                        title = currentBook.title,
                        description = currentBook.description ?: "",
                        imageUrl = currentBook.imageUrl ?: "",
                        userId = auth.currentUser?.uid ?: "anonymous"
                    )
                )
            }
            _state.value = _state.value.copy(isFavorite = !_state.value.isFavorite)
        }
    }
}
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


// TODO: Нельзя инжектить одну вьюмодель в другую
class BookDetailViewModel(
    private val catalogViewModel: BookCatalogViewModel, // для доступа к списку книг из каталога
    private val favoriteViewModel: FavoriteViewModel, // для работы с избранными книгами
    private val repository: BookRepository // для загрузки книги по id из API
) : ViewModel() {

    // TODO: Shared/StateFlow знать на изусть
    private val _state = MutableStateFlow(BookDetailState()) // внутреннее состояние экрана
    val state: StateFlow<BookDetailState> = _state // публичное состояние для UI

    init { // Подписка на изменения избранного пользователя
        viewModelScope.launch {
            favoriteViewModel.favorites.collect { favs ->
                val currentBook = _state.value.book
                if (currentBook != null) {
                    val isFav = favs.any { it.id == currentBook.id }
                    _state.value = _state.value.copy(isFavorite = isFav)
                }
            }
        }
    }

    // Загружает книгу по bookId
    // Сначала проверяет каталог потом избранное, потом API
    fun loadBook(bookId: String) {
        viewModelScope.launch {
            // сначала ищу  в каталоге
            val fromCatalog = catalogViewModel.getBookById(bookId)
            if (fromCatalog != null) {
                _state.value = BookDetailState(
                    book = fromCatalog,
                    isFavorite = favoriteViewModel.favorites.value.any { it.id == bookId }
                )
                return@launch
            }

            // если нету в каталоге, то ищу в избранном
            val fromFav = favoriteViewModel.favorites.value.find { it.id == bookId }
            if (fromFav != null) {
                _state.value = BookDetailState(
                    book = com.example.domain.model.Book(
                        id = fromFav.id,
                        title = fromFav.title,
                        authors = emptyList(),
                        description = fromFav.description,
                        imageUrl = fromFav.imageUrl
                    ),
                    isFavorite = true
                )
                return@launch
            }

            // если нет нигде, то запрос в сеть
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

    // Добавляет или удаляет текущую книгу из избранного
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
            // Обновляем локальное состояние, чтобы UI сразу отразил изменения
            _state.value = _state.value.copy(isFavorite = !_state.value.isFavorite)
        }
    }
}
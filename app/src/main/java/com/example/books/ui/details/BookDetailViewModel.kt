package com.example.books.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FavoriteBook
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.domain.repository.BookRepository
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


// TODO: Нельзя инжектить одну вьюмодель в другую
class BookDetailViewModel( // для доступа к списку книг из каталога
    private val favoriteRepository: FavoriteRepository,
    private val auth: FirebaseAuth, // для работы с избранными книгами
    private val repository: BookRepository // для загрузки книги по id из API
) : ViewModel() {

    // TODO: Shared/StateFlow знать на изусть
    // MutableStateFlow хранит текущее состояние экрана и позволяет наблюдать за ним
    // Внутренний state используется для изменения данных внутри ViewModel
    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state

    init { // Подписка на изменения избранного пользователя
        viewModelScope.launch {
            favoriteRepository.getFavorites(auth.currentUser?.uid ?: "anonymous") // Получаем поток избранного пользователя
                .collect { favs -> // collect запускает наблюдение за изменениями
                    val currentBook = _state.value.book // получаем текущую книгу на экране
                    if (currentBook != null) { // если книга уже загружена
                        // Проверяем, есть ли она в списке избранного
                        val isFav = favs.any { it.id == currentBook.id }
                        _state.value = _state.value.copy(isFavorite = isFav)// Обновляем состояние UI, чтобы сразу отобразить, что книга избранная
                    }
                }
        }
    }

    // Загружает книгу по bookId
    fun loadBook(bookId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: "anonymous"// Получаем ID пользователя

            //Проверяем сначала избранное
            val favs = favoriteRepository.getFavorites(userId).first()
            val fromFav = favs.find { it.id == bookId }

            if (fromFav != null) {
                // если книга есть в избранном
                // Создаем объект Book на основе данных FavoriteBook
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

            //Если книги нет в избранном грузим из API
            val fromApi = repository.getBookById(bookId)
            if (fromApi != null) {
                _state.value = BookDetailState(
                    book = fromApi,
                    isFavorite = favs.any { it.id == bookId }// проверяем, есть ли она в избранном
                )
            } else {// если книга не найдена ни в избранном, ни в API
                _state.value = BookDetailState(book = null, isFavorite = false)
            }
        }
    }


    // Добавляет или удаляет текущую книгу из избранного
    fun toggleFavorite() {
        val currentBook = _state.value.book ?: return
        viewModelScope.launch {
            if (_state.value.isFavorite) {// если книга уже в избранном
                // Удаляем из избранного через репозиторий
                favoriteRepository.removeFromFavorites(
                    currentBook.id,
                    auth.currentUser?.uid ?: "anonymous"
                )
            } else {// если книги нет в избранном
                // Добавляем в избранное
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
        }
    }
}
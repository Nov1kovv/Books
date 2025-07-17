package com.example.books.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.details.RetrofitClient
import com.example.books.data.repository.BookRepositoryImpl
import com.example.books.domain.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookCatalogViewModel : ViewModel() {

    private val repository = BookRepositoryImpl(RetrofitClient.api) //репозиторий для получения книг из API
    private val _books = MutableStateFlow<List<Book>>(emptyList()) // Приватный поток для хранения списка книг (изменяется только внутри ViewModel)
    val books: StateFlow<List<Book>> = _books // Публичный поток, за которым может следить UI (например, Compose)

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchBooks(query)
                _books.value = result
            } catch (e: Exception) {
                // Обработка ошибки
                _books.value = emptyList()
            }
        }
    }
    fun getBookById(bookId: String): Book? {
        return _books.value.find { it.id == bookId }
    }
}
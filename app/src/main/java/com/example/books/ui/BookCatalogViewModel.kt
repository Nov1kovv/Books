package com.example.books.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookCatalogViewModel( private val repository: BookRepository) : ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchBooks(query)
                _books.value = result
            } catch (e: Exception) {
                _books.value = emptyList()
            }
        }
    }
    fun getBookById(bookId: String): Book? {
        return _books.value.find { it.id == bookId }
    }
}
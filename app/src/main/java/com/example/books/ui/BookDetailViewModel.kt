package com.example.books.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository,
    private val bookId: String
) : ViewModel() {

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book
}
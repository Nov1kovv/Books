package com.example.books.ui.search.mvi

import com.example.domain.model.Book

data class BookCatalogState(
    val query: String = "",
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedBook: Book? = null
)

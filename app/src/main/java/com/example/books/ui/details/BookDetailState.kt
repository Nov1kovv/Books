package com.example.books.ui.details

import com.example.domain.model.Book

data class BookDetailState(
    val book: Book? = null,
    val isFavorite: Boolean = false
)
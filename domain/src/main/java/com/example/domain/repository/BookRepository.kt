package com.example.books.domain.repository

import com.example.books.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String): List<Book>
}
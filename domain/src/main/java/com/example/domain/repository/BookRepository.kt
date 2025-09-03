package com.example.domain.repository

import com.example.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String, startIndex: Int = 0, maxResults: Int = 20): List<Book>
    suspend fun getBookById(id: String): Book?
}
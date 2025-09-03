package com.example.domain.repository

import com.example.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String): List<Book>
    suspend fun getBookById(id: String): Book?
}
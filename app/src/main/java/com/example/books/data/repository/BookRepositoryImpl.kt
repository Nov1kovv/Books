package com.example.books.data.repository

import com.example.books.data.api.GoogleBooksApi
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository

class BookRepositoryImpl(
    private val api: GoogleBooksApi
) : BookRepository {
    override suspend fun searchBooks(query: String): List<Book> {
        val response = api.searchBooks(query)
        return response.items.map {
            Book(
                id = it.id,
                title = it.volumeInfo.title,
                authors = it.volumeInfo.authors,
                description = it.volumeInfo.description,
                imageUrl = it.volumeInfo.imageLinks?.thumbnail
            )
        }
    }
}
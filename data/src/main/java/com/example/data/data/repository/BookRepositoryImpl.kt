package com.example.books.data.repository

import com.example.books.data.api.GoogleBooksApi
import com.example.domain.model.Book
import com.example.domain.repository.BookRepository

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
    override suspend fun getBookById(id: String): Book? {
        return try {
            val item = api.getVolume(id)
            Book(
                id = item.id,
                title = item.volumeInfo.title,
                authors = item.volumeInfo.authors,
                description = item.volumeInfo.description,
                imageUrl = item.volumeInfo.imageLinks?.thumbnail
            )
        } catch (e: Exception) {
            null
        }
    }
}
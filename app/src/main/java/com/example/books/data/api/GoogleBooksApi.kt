package com.example.books.data.api

import com.example.books.data.model.BooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BooksResponseDto
}
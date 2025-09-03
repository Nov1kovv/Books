package com.example.books.data.api

import com.example.books.data.model.BooksResponseDto
import com.example.books.data.model.Item
import com.example.books.data.model.VolumeInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BooksResponseDto

    @GET("volumes/{id}")
    suspend fun getVolume(@Path("id") id: String): Item
}
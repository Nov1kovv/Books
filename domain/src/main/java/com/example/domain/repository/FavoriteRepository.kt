package com.example.domain.repository

import com.example.domain.model.FavoriteBook
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(userId: String): Flow<List<FavoriteBook>>
    suspend fun addToFavorites(book: FavoriteBook)
    suspend fun removeFromFavorites(bookId: String, userId: String)
}

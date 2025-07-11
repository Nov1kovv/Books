package com.example.books.data.model

import com.google.gson.annotations.SerializedName

data class BooksResponseDto(
    @SerializedName("kind") val kind: String,
    @SerializedName("totalItems") val totalItems: Long,
    @SerializedName("items") val items: List<Item>
)

data class Item(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    @SerializedName("title") val title: String,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("description") val description: String?
)
package com.example.data.data.model

import com.example.books.data.model.ImageLinks
import com.google.gson.annotations.SerializedName

data class VolumeInfoDto(
    @SerializedName("title") val title: String,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("description") val description: String?,
    @SerializedName("imageLinks") val imageLinks: ImageLinks?
)
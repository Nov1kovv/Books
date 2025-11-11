package com.example.data.data.model

import com.example.books.data.model.VolumeInfo
import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo
)
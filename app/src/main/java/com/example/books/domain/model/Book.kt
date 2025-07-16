package com.example.books.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val description: String?
)
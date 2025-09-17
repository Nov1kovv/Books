package com.example.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageUrl: String?,
)

// TODO: После переделки авторизации на repository скорее всего добавятся доп сущности, но это не точнол
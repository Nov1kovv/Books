package com.example.books.ui.state

import com.example.books.domain.model.Book

sealed class BookCatalogAction {
    data class Search(val query: String) : BookCatalogAction()
    data class SelectBook(val book: Book) : BookCatalogAction()
}
package com.example.books.ui.search.mvi

import com.example.domain.model.Book

sealed class BookCatalogAction {
    data class Search(val query: String) : BookCatalogAction()
    data class SelectBook(val book: Book) : BookCatalogAction()
}
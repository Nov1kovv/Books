package com.example.books.ui.search.mvi

sealed class BookCatalogSideEffect {
    data class ShowError(val message: String) : BookCatalogSideEffect()
    data class NavigateToDetail(val bookId: String) : BookCatalogSideEffect()
}
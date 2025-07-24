package com.example.books.ui.state

sealed class BookCatalogSideEffect {
    data class ShowError(val message: String) : BookCatalogSideEffect()
    data class NavigateToDetail(val bookId: String) : BookCatalogSideEffect()
}
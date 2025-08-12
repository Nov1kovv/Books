package com.example.books.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import com.example.books.ui.search.mvi.BookCatalogAction
import com.example.books.ui.search.mvi.BookCatalogSideEffect
import com.example.books.ui.search.mvi.BookCatalogState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class SearchViewModel(
    private val repository: BookRepository
) : ViewModel(), ContainerHost<BookCatalogState, BookCatalogSideEffect> {

    override val container = container<BookCatalogState, BookCatalogSideEffect>(
        BookCatalogState()
    )

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        intent {
            reduce { state.copy(isLoading = false, error = throwable.message) }
            postSideEffect(BookCatalogSideEffect.ShowError(throwable.message ?: "Ошибка") )
        }
    }

//    init {
//        searchBooks("nasa")
//    }

    fun dispatch(action: BookCatalogAction) {
        when (action) {
            is BookCatalogAction.Search -> searchBooks(action.query)
            is BookCatalogAction.SelectBook -> handleSelectBook(action.book)
        }
    }

    private fun searchBooks(query: String) = intent {
        try {
            reduce { state.copy(isLoading = true, error = null, query = query) }
            val result = repository.searchBooks(query)
            reduce { state.copy(books = result, isLoading = false) }
        } catch (e: Exception) {
            reduce { state.copy(isLoading = false, error = e.message) }
            postSideEffect(BookCatalogSideEffect.ShowError(e.message ?: "Ошибка"))
        }
    }

    private fun handleSelectBook(book: Book) = intent {
        reduce { state.copy(selectedBook = book) }
        postSideEffect(BookCatalogSideEffect.NavigateToDetail(book.id))
    }
    fun getBookById(bookId: String): Book? {
        return container.stateFlow.value.books.find { it.id == bookId }
    }
}





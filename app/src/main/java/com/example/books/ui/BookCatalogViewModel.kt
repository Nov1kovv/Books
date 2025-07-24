package com.example.books.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.domain.model.Book
import com.example.books.domain.repository.BookRepository
import com.example.books.ui.state.BookCatalogAction
import com.example.books.ui.state.BookCatalogSideEffect
import com.example.books.ui.state.BookCatalogState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class BookCatalogViewModel( private val repository: BookRepository) : ViewModel(),
    ContainerHost<BookCatalogState, BookCatalogSideEffect> {

        override val container = container<BookCatalogState, BookCatalogSideEffect>(
        BookCatalogState()
    )

    init {
        dispatch(BookCatalogAction.Search(""))
    }

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        intent {
            reduce { state.copy(isLoading = false, error = throwable.message) }
            postSideEffect(BookCatalogSideEffect.ShowError(throwable.message ?: "Ошибка"))
        }
    }

    fun dispatch(action: BookCatalogAction) {
        when (action) {
            is BookCatalogAction.Search -> searchBooks(action.query)
            is BookCatalogAction.SelectBook -> handleSelectBook(action.book)
            }
        }

    private fun searchBooks(query: String) = intent {
        viewModelScope.launch(ceh) {
            reduce { state.copy(isLoading = true, error = null, query = query) }
            val result = repository.searchBooks(query)
            reduce { state.copy(books = result, isLoading = false) }
        }
    }

    private fun handleSelectBook(book: Book) = intent {
        viewModelScope.launch(ceh) {
            reduce { state.copy(selectedBook = book) }
            postSideEffect(BookCatalogSideEffect.NavigateToDetail(book.id))
        }
    }

    fun getBookById(bookId: String): Book? {
        return container.stateFlow.value.books.find { it.id == bookId }
    }
}
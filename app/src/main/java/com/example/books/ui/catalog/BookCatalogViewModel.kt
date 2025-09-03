package com.example.books.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.ui.catalog.mvi.BookCatalogAction
import com.example.books.ui.catalog.mvi.BookCatalogSideEffect
import com.example.books.ui.catalog.mvi.BookCatalogState
import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class BookCatalogViewModel(private val repository: BookRepository) : ViewModel(),
    ContainerHost<BookCatalogState, BookCatalogSideEffect> {

    private var currentPage = 0
    private val pageSize = 20
    private var isLastPage = false
    private var currentQuery = ""

    override val container = container<BookCatalogState, BookCatalogSideEffect>(
        BookCatalogState()
    )

    init {
        dispatch(BookCatalogAction.Search("nasa"))
    }

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        intent {
            reduce { state.copy(isLoading = false, error = throwable.message) }
            postSideEffect(BookCatalogSideEffect.ShowError(throwable.message ?: "Ошибка"))
        }
    }

    fun dispatch(action: BookCatalogAction) { //функция обрабатывающая пользовательские события
        when (action) {
            is BookCatalogAction.Search -> startSearch(action.query)
            is BookCatalogAction.SelectBook -> handleSelectBook(action.book)
        }
    }

    private fun startSearch(query: String) = intent {
        currentQuery = query
        currentPage = 0
        isLastPage = false
        reduce { state.copy(query = query, books = emptyList()) } // очищаем старые результаты
        loadNextPage()
    }

    fun loadNextPage() = intent {
        if (isLastPage || state.isLoading) return@intent
        viewModelScope.launch(ceh) {
            reduce { state.copy(isLoading = true, error = null) }
            val books = repository.searchBooks(currentQuery, currentPage * pageSize, pageSize)
            isLastPage = books.size < pageSize
            currentPage++
            reduce {
                state.copy(
                    books = state.books + books,
                    isLoading = false
                )
            }
        }
    }

//    private fun searchBooks(query: String) = intent {
//        viewModelScope.launch(ceh) { //запускается корутина с обработкой от ошибок (ceh)
//            reduce {
//                state.copy(
//                    isLoading = true,
//                    error = null,
//                    query = query
//                )
//            }//Меняет состояние state то, что нужно отобразить на экране
//            val result = repository.searchBooks(query)
//            reduce {
//                state.copy(
//                    books = result,
//                    isLoading = false
//                )
//            }//Меняет состояние state то, что нужно отобразить на экране
//        }
//    }

    private fun handleSelectBook(book: Book) =
        intent { //сохраняет выбранную книгу в состояние и навигирует к Detail экрану
            viewModelScope.launch(ceh) { //запускается корутина с обработкой от ошибок (ceh)
                reduce { state.copy(selectedBook = book) } //состояние обновляется, выбранная книга сохраняется в state.selectedBook
                postSideEffect(BookCatalogSideEffect.NavigateToDetail(book.id))
            }
        }

    fun getBookById(bookId: String): Book? {
        return container.stateFlow.value.books.find { it.id == bookId }
    }
}
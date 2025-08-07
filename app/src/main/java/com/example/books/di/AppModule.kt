package com.example.books.di

import com.example.books.data.api.GoogleBooksApi
import com.example.books.data.repository.BookRepositoryImpl
import com.example.books.domain.repository.BookRepository
import com.example.books.ui.bottombar.ProfileViewModel
import com.example.books.ui.catalog.BookCatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<GoogleBooksApi> {
        get<Retrofit>().create(GoogleBooksApi::class.java)
    }

    single<BookRepository> {
        BookRepositoryImpl(api = get())
    }

    viewModel {
        BookCatalogViewModel(repository = get())
    }
    viewModel {
        ProfileViewModel()
    }
}
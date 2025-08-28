package com.example.books.di

import com.example.books.data.api.GoogleBooksApi
import com.example.books.data.repository.BookRepositoryImpl
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.domain.repository.BookRepository
import com.example.books.ui.bottombar.profile.ProfileViewModel
import com.example.books.ui.catalog.BookCatalogViewModel
import com.example.books.ui.registration.RegistrationViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //логирование запрос inreceptor
        //если rx java, то будет flow intereptor что-то такое
        //смотрю запрос,
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
    viewModel {
        RegistrationViewModel()
    }
    viewModel {
        FavoriteViewModel()
    }
}
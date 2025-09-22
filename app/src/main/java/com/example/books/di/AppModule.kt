package com.example.books.di

import com.example.books.data.api.GoogleBooksApi
import com.example.books.data.repository.BookRepositoryImpl
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.domain.repository.BookRepository
import com.example.books.ui.bottombar.profile.ProfileViewModel
import com.example.books.ui.catalog.BookCatalogViewModel
import com.example.books.ui.details.BookDetailViewModel
import com.example.books.authorization.registration.RegistrationViewModel
import com.example.data.data.repository.AuthorizationRepositoryImpl
import com.example.data.data.repository.FavoriteRepositoryImpl
import com.example.domain.repository.AuthorizationRepository
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    single { FirebaseAuth.getInstance() }

    single<AuthorizationRepository> {
        AuthorizationRepositoryImpl(get())
    }
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            firestore = FirebaseFirestore.getInstance(),
            auth = get()
        )
    }

    viewModel {
        BookCatalogViewModel(repository = get())
    }
    viewModel {
        ProfileViewModel()
    }
    viewModel {
        RegistrationViewModel(repository = get())
    }
    viewModel {
        FavoriteViewModel(
            repository = get(),
            auth = get())
    }
    viewModel {
        BookDetailViewModel(
            repository = get(),
            favoriteRepository = get(),
            auth = get()
        )
    }
}

// TODO: Раздели на 2 модуля. Все что для network в network module

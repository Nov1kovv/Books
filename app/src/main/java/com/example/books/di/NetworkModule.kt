package com.example.books.di

import com.example.books.data.api.GoogleBooksApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

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
    }

    single<GoogleBooksApi> {
        get<Retrofit>().create(GoogleBooksApi::class.java)
    }
}
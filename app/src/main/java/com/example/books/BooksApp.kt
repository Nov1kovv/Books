package com.example.books

import android.app.Application
import com.example.books.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BooksApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@BooksApp)
            modules(appModule)
        }
    }
}

// TODO: Реализовать feature api-impl архитектуру с помощью Игоря в самом конце
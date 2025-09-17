package com.example.domain.repository

import com.example.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String, startIndex: Int = 0, maxResults: Int = 20): List<Book>
    suspend fun getBookById(id: String): Book?
    // TODO:  Вся бизнес логика (методы) которые использует приложение должны быть тут (я должен открыть проект по всем репозиториям понять как он работает)
    // TODO: Для начала надо знать что такое бизнес логика, а что UI. Ответ есть в моем конспекте по архитектуре
    // TODO: Этот проект будет на репозиториях, а nasa на UseCase. Понять отличия и знать что такое Interactor.
}
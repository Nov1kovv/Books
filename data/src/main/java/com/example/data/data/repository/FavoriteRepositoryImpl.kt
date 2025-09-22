package com.example.data.data.repository

import com.example.domain.model.FavoriteBook
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// Реализация репозитория избранных книг через Firebase Firestore.
// Использует Flow для того, чтобы UI автоматически обновлялся при изменениях в базе.
class FavoriteRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth // FirebaseAuth для получения текущего пользователя
) : FavoriteRepository {

    // Автоматически берёт ID текущего пользователя из FirebaseAuth
    // Если пользователь авторизован возвращает его uid
    // Если currentUser == null возвращает "anonymous"
    private val userId: String
        get() = auth.currentUser?.uid ?: "anonymous"

    // Метод для получения списка избранных книг пользователя.
    // Возвращает Flow это поток, который будет эмитить актуальные данные при любых изменениях в Firestore.
    override fun getFavorites(userId: String): Flow<List<FavoriteBook>> = callbackFlow {
        // Делаю подписку на коллекцию favorites в Firestore.
        // Фильтруею документы по userId, чтобы получить только избранные книги текущего пользователя.
        val subscription = firestore.collection("favorites")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ -> // Слушатель изменений в коллекции
                if (snapshot != null) {
                    // Преобразуем все документы в объекты FavoriteBook.
                    // mapNotNull — игнорирует документы, которые не удалось преобразовать
                    val books = snapshot.documents.mapNotNull {
                        it.toObject(FavoriteBook::class.java)
                    }
                    // Отправляем список книг в Flow, чтобы все подписчики получили обновление
                    trySend(books)
                }
            }
        // awaitClose вызывается при закрытии Flow когда ViewModel уничтожается
        // Останавливаем подписку на Firestore, чтобы избежать утечек памяти
        awaitClose { subscription.remove() }
    }

    // Метод для добавления книги в избранное.
    // Сначала создаём копию книги с привязанным userId текущего пользователя,
    // чтобы в Firestore можно было фильтровать книги по пользователю.
    override suspend fun addToFavorites(book: FavoriteBook) {
        val bookWithUser = book.copy(userId = userId)
        firestore.collection("favorites")
            .document("${userId}_${book.id}") //ключ userId + bookId
            .set(bookWithUser)// Записываем документ в Firestore
    }
    // Метод для удаления книги из избранного по её ID и userId.
    // Строка "${userId}_$bookId" должна совпадать с тем, как мы создавали документ при добавлении.
    override suspend fun removeFromFavorites(bookId: String, userId: String) {
        firestore.collection("favorites")
            .document("${userId}_$bookId")// Находим документ по ID
            .delete()
    }
}
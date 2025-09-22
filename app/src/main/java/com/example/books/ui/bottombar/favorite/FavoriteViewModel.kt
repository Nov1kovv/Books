package com.example.books.ui.bottombar.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FavoriteBook
import com.example.domain.repository.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Обеспечивает подписку на изменения в избранных книгах через StateFlow.
// Из за этого UI автоматически обновляется при добавлении или удалении книг в Firestore
class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val auth: FirebaseAuth // Для получения текущего пользователя
) : ViewModel() {

   //Хранит и предоставляет поток данных favorites
    val favorites: StateFlow<List<FavoriteBook>> =
        repository.getFavorites(auth.currentUser?.uid ?: "anonymous")// Получаем поток избранных книг текущего пользователя

    //StateIn - это оператор Kotlin Flow, который преобразует обычный Flow в StateFlow.
    //StateFlow — это поток, который хранит текущее значение и всегда отдает его любому новому подписчику.
    //В отличие от обычного Flow, новый подписчик сразу получает последнее значение без ожидания следующего эмита.
    //viewModelScope это CoroutineScope, который привязан к жизненному циклу ViewModel.
            // Все корутины внутри него автоматически отменяются, когда ViewModel уничтожается.
            .stateIn(viewModelScope,
                SharingStarted.Lazily, //Lazily означает что поток не начнёт эмитить значения, пока нет хотя бы одного подписчика.
                emptyList()) //Начальное значение для StateFlow.
    //Пока данные из Firestore не пришли, любому подписчику будет сразу доступен пустой список.
    //Нужно чтобы UI не падал из-за null и мог сразу отобразить пустое состояние.
}
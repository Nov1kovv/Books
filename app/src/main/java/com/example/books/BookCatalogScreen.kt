package com.example.books

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.books.data.details.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BookCatalogScreen() {
    var query by remember { mutableStateOf("") } // query хранит текст введённый в поле поиска
    var isSearching by remember { mutableStateOf(false) }  // isSearching флаг указывающий нужно ли запустить поиск

    Column(
        modifier = Modifier
            .fillMaxSize()// Заполняет весь экран
            .padding(16.dp),// Внутренний отступ от краёв
        verticalArrangement = Arrangement.Top // Элементы начинаются сверху
    ) {
        Text("Каталог книг", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = query,
            onValueChange = { query = it },//Обновление значения при вводе
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                isSearching = true //флаг, чтобы запустить поиск
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Искать")
        }

        //Если isSearching = true, запускаем эффект поиска
        if (isSearching) {
            // LaunchedEffect запускается при изменении query
            LaunchedEffect(query) {
                try {
                    val response = RetrofitClient.api.searchBooks(query)
                    Log.d("BookSearch", "Найдено книг ${response.totalItems}")
                    response.items.forEach {
                        Log.d("BookSearch", "Книга ${it.volumeInfo.title}")
                    }
                } catch (e: Exception) {
                    Log.e("BookSearch", "Ошибка ${e.message}", e)
                }
                isSearching = false
            }
        }
    }
}

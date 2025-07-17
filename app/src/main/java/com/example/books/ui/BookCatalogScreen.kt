package com.example.books.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.items

@Composable
fun BookCatalogScreen(viewModel: BookCatalogViewModel) {
    var query by remember { mutableStateOf("") } // query хранит текст введённый в поле поиска
    val books by viewModel.books.collectAsState()
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
            onClick = { viewModel.searchBooks(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Искать")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список книг с изображениями
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(books) { book ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = book.title)

                    Spacer(modifier = Modifier.height(4.dp))

                    val imageUrl = book.imageUrl
                    Log.d("BookCatalogScreen", "imageUrl = $imageUrl")

                    if (imageUrl != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = book.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                    } else {
                        Text("Нет изображения")
                    }
                }
            }
        }
    }
}
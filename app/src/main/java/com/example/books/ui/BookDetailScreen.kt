package com.example.books.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun BookDetailScreen(bookId: String, viewModel: BookCatalogViewModel) {
    val book = viewModel.getBookById(bookId)
    if (book == null) {
        Text("Книга не найдена")
        return
    }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title)
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = book.description ?: "Описание недоступно")
            Spacer(modifier = Modifier.height(16.dp))

            if (book.imageUrl != null) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = book.title,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
            }
        }
    }
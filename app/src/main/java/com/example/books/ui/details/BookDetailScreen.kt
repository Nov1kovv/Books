package com.example.books.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.books.ui.catalog.BookCatalogViewModel

@Composable
fun BookDetailScreen(bookId: String, viewModel: BookCatalogViewModel) {
    val book = viewModel.getBookById(bookId)
    if (book == null) {
        Text("Книга не найдена")
        return
    }
        Column(modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (book.imageUrl != null) {
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = book.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(12.dp)) //скругленные углы
                            .padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                Column {
                    Text(text = book.title)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = book.description ?: "Описание недоступно")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
}
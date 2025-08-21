package com.example.books.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.books.ui.catalog.BookCatalogViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun BookDetailScreen(bookId: String, viewModel: BookCatalogViewModel) {
    val state by viewModel.collectAsState()
    val book = state.books.find { it.id == bookId }

    if (book == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center

        ) {
            Text("Книга не найдена", style = MaterialTheme.typography.titleMedium,
                color = Color.White)
        }
        return
    }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
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
                            .size(width = 160.dp, height = 220.dp)
                            .clip(RoundedCornerShape(12.dp)) //скругленные углы
                            .padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }

                Column {
                    Text(text = book.title, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = book.description ?: "Описание недоступно", color = Color(0xFFCCCCCC),)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
}
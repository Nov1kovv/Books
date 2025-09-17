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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun BookDetailScreen(viewModel: BookDetailViewModel, bookId: String) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }

    val book = state.book
    if (book == null) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            Text("Книга не найдена", color = Color.White)
            // TODO: все строки в ресурсы, поищи по кавычкам поиском
        }
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            if (book.imageUrl != null) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = book.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp, 220.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(2.dp)
                )
                Spacer(Modifier.width(20.dp))
            }

            Column {
                Text(book.title, color = Color.White)
                Spacer(Modifier.height(12.dp))
                Text(book.description ?: "Описание недоступно", color = Color(0xFFCCCCCC))
            }
        }

        Spacer(Modifier.height(16.dp))
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(onClick = { viewModel.toggleFavorite() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E1E1E),
                    contentColor = Color.White
                )
                ) {
                Text(if (state.isFavorite) "Убрать из избранного" else "Добавить в избранное",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
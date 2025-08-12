package com.example.books.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.books.ui.bottombar.BottomNavigationBar
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun BookCatalogScreen(navController: NavController, viewModel: BookCatalogViewModel) {
    val state by viewModel.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // отступ от bottomBar
                .padding(16.dp)
            //.systemBarsPadding() это будет на деталке
            //здесь сделать scaffold
        ) {
            Text("Каталог книг", modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController?.navigate("search") },
                placeholder = { Text("Нажмите, чтобы искать книги") }
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(state.books) { book ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        if (book.imageUrl != null) {
                            AsyncImage(
                                model = book.imageUrl,
                                contentDescription = book.title,
                                contentScale = ContentScale.Crop, //обрезание под размер
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(RoundedCornerShape(12.dp)) //скругление картинки
                                    .clickable {
                                        navController.navigate("detail/${book.id}")
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = book.title)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = book.description ?: "Описание недоступно", maxLines = 3)
                        }

                    }
                }
            }
        }
    }
}
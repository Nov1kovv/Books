package com.example.books.ui.search

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.books.ui.BookCatalogViewModel
import com.example.books.ui.search.mvi.BookCatalogAction
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun SearchScreen(viewModel: BookCatalogViewModel, navController: NavController) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F3F4))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F3F4))
                .padding(16.dp)
        ) {
            TextField(
                value = state.query,// текущее значение текста в поле
                onValueChange = { viewModel.dispatch(BookCatalogAction.Search(it)) },// вызывается при изменении текста, обновляет состояние query
                placeholder = { Text("Введите название книги") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search), //ввод на кнопке заменяется на поискк
                keyboardActions = KeyboardActions( //это обработка действий с клавиатуры
                    onSearch = {
                        if (state.query.isNotBlank()) {//если query не пустое то вызывается функции searchBooks
                            viewModel.dispatch(BookCatalogAction.Search(state.query))
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


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
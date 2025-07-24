package com.example.books.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.books.ui.state.BookCatalogAction
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun SearchScreen(viewModel: BookCatalogViewModel, navController: NavController) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = state.query,// текущее значение текста в поле
            onValueChange = { viewModel.dispatch(BookCatalogAction.Search(it))},// вызывается при изменении текста, обновляет состояние query
            placeholder = { Text("Введите название книги") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
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
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = book.title)

                    Spacer(modifier = Modifier.height(4.dp))

                    val imageUrl = book.imageUrl
                    Log.d("BookCatalogScreen", "imageUrl = $imageUrl")

                    if (imageUrl != null) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = book.title,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .clickable {
                                    navController.navigate("detail/${book.id}")
                                }
                        )
                    } else {
                        Text("Нет изображения")
                    }
                }
            }
        }
    }
}
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
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.books.ui.search.mvi.BookCatalogAction
import org.orbitmvi.orbit.compose.collectAsState

//@Composable
//fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
//    val state by viewModel.collectAsState()
//
//    val backgroundColor = Color(0xFF121212)
//    val cardColor = Color(0xFF1E1E1E)
//    val textPrimary = Color.White
//    val textSecondary = Color(0xFFB0B0B0)
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()// Box занимает весь экран
//            .background(backgroundColor)
//            .padding(16.dp)// отступ от краёв экрана
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .systemBarsPadding() // отступ сверху для статус бара и навигационной панели
//                .background(backgroundColor)
//                .padding(16.dp)
//        ) {
//            Text(
//                "Поиск книг",
//                color = textPrimary,
//                fontSize = 24.sp, // размер текста заголовка
//                fontWeight = FontWeight.Bold, // жирынй цвет
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            TextField(
//                value = state.query,// текущее значение текста в поле
//                onValueChange = { query ->
//                    viewModel.dispatch(BookCatalogAction.Search(query))
//                                },
//                placeholder = { Text("Введите название книги") },
//                singleLine = true, // одна строка текста
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "Поиск",
//                        tint = textSecondary // менее яркая
//                    )
//                },
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = cardColor, // фон при фокусе
//                    unfocusedContainerColor = cardColor,// фон без фокуса
//                    focusedTextColor = textPrimary, // цвет текста
//                    unfocusedTextColor = textPrimary,
//                    focusedPlaceholderColor = textSecondary, // цвет подсказки
//                    unfocusedPlaceholderColor = textSecondary,
//                    focusedIndicatorColor = Color.Transparent, // убираем линию снизу
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(12.dp)),
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search), //ввод на кнопке заменяется на поискк
//                keyboardActions = KeyboardActions( //это обработка действий с клавиатуры
//                    onSearch = {
//                        if (state.query.isNotBlank()) {//если query не пустое то вызывается функции searchBooks
//                            viewModel.dispatch(BookCatalogAction.Search(state.query))
//                        }
//                    }
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//
//            LazyColumn(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) {
//                items(state.books) { book ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(12.dp))
//                            .padding(10.dp)
//                    ) {
//                        if (book.imageUrl != null) {
//                            AsyncImage(
//                                model = book.imageUrl,
//                                contentDescription = book.title,
//                                contentScale = ContentScale.Crop, //обрезание под размер
//                                modifier = Modifier
//                                    .size(120.dp)
//                                    .clip(RoundedCornerShape(8.dp)) //скругление картинки
//                                    .clickable {
//                                        navController.navigate("detail/${book.id}")
//                                    }
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.width(12.dp))
//
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(text = book.title, color = textPrimary,
//                                fontWeight = FontWeight.SemiBold,
//                                fontSize = 16.sp)
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text(text = book.description ?: "Описание недоступно",color = textSecondary, fontSize = 14.sp, maxLines = 3)
//                        }
//                    }
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(12.dp))
//    }
//}
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.books.R
import com.example.books.ui.bottombar.BottomNavigationBar
import com.example.books.ui.catalog.mvi.BookCatalogAction
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun BookCatalogScreen(navController: NavController, viewModel: BookCatalogViewModel) {
    val state by viewModel.collectAsState()
    val listState = rememberLazyListState()
    val backgroundColor = Color(0xFF121212)// основной фон экрана
    val cardColor = Color(0xFF1E1E1E)//фон карточек книги
    val textPrimary = Color.White // основной цвет текста
    val textSecondary = Color(0xFFB0B0B0)// второстепенный текст

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding -> // innerPadding это отступы, чтобы контент не перекрывался bottomBar

        Column(
            modifier = Modifier
                .fillMaxSize() // колонка занимает весь экран
                .padding(innerPadding) // отступ от bottomBar
                .padding(16.dp) //внутренние отступы контента
                .background(backgroundColor)
        ) {
            Text(
                stringResource(R.string.catalog_title), color = textPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold, // жирный шрифт
                modifier = Modifier.fillMaxWidth()
            ) // текст растягивается на всю ширину

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.query,
                onValueChange = { query ->
                    viewModel.dispatch(BookCatalogAction.Search(query))
                },
                placeholder = { Text(stringResource(R.string.search_placeholder)) },
                singleLine = true, // одна строка текста
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon_desc),
                        tint = textSecondary // менее яркая
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = cardColor, // фон при фокусе
                    unfocusedContainerColor = cardColor,// фон без фокуса
                    focusedTextColor = textPrimary, // цвет текста
                    unfocusedTextColor = textPrimary,
                    focusedPlaceholderColor = textSecondary, // цвет подсказки
                    unfocusedPlaceholderColor = textSecondary,
                    focusedIndicatorColor = Color.Transparent, // убираем линию снизу
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth() // занимает всю ширину
                    .clip(RoundedCornerShape(12.dp)), //скругление
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search), //ввод на кнопке заменяется на поискк
                keyboardActions = KeyboardActions( //это обработка действий с клавиатуры
                    onSearch = {
                        if (state.query.isNotBlank()) {//если query не пустое то вызывается функции searchBooks
                            viewModel.dispatch(BookCatalogAction.Search(state.query))
                        }
                    }
                )
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f) // занимает оставшееся пространство экрана
                    .fillMaxWidth() // растягивается на всю ширину
            ) {
                items(state.books) { book ->
                    Row(
                        verticalAlignment = Alignment.Top, // выравниваем по верхнему краю
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .padding(10.dp) // внутренние отступы
                    ) {
                        if (book.imageUrl != null) {
                            AsyncImage(
                                model = book.imageUrl,
                                contentDescription = book.title,
                                contentScale = ContentScale.Crop, //обрезание под размер
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp)) //скругление картинки
                                    .clickable {
                                        navController.navigate("detail/${book.id}")
                                    }
                                    .placeholder(
                                        visible = state.isLoading,
                                        highlight = PlaceholderHighlight.shimmer(),
                                        color = Color.DarkGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = book.title, color = textPrimary,
                                fontWeight = FontWeight.SemiBold, // полужирный текст
                                fontSize = 16.sp
                            ) // размер шрифта
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = book.description ?:stringResource(R.string.description_unavailable),
                                color = textSecondary,
                                fontSize = 14.sp,
                                maxLines = 3
                            )
                        }

                    }
                }
                if (state.isLoading) {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.loading), color = Color.White)
                        }
                    }
                }
            }

            // отслеживаем скролл для подгрузки следующей страницы
            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .filterNotNull()
                    .distinctUntilChanged()
                    .collectLatest { lastVisibleIndex ->
                        val totalItems = state.books.size
                        if (lastVisibleIndex >= totalItems - 5) { // 5 элементов до конца
                            viewModel.loadNextPage()
                        }
                    }
            }
        }
    }
}

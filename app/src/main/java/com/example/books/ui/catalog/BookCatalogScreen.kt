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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.books.ui.bottombar.BottomNavigationBar
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun BookCatalogScreen(navController: NavController, viewModel: BookCatalogViewModel) {
    val state by viewModel.collectAsState()

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
            Text("Каталог книг", color = textPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold, // жирный шрифт
                modifier = Modifier.fillMaxWidth()) // текст растягивается на всю ширину

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "",
                onValueChange = {},
                enabled = false,
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = cardColor, // фон неактивного поля
                    disabledTextColor = textPrimary, // цвет текста
                    disabledPlaceholderColor = textSecondary, // цвет подсказки
                    focusedIndicatorColor = Color.Transparent, // убираем линию при фокусе
                    unfocusedIndicatorColor = Color.Transparent // убираем линию без фокуса
                ),
                modifier = Modifier
                    .fillMaxWidth() // занимает всю ширину
                    .clip(RoundedCornerShape(12.dp)) //скругление
                    .clickable { navController?.navigate("search") },
                placeholder = { Text("Нажмите, чтобы искать книги") }
            )

            LazyColumn(
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
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = book.title,color = textPrimary,
                                fontWeight = FontWeight.SemiBold, // полужирный текст
                                fontSize = 16.sp) // размер шрифта
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = book.description ?: "Описание недоступно",color = textSecondary, fontSize = 14.sp, maxLines = 3)
                        }

                    }
                }
            }
        }
    }
}
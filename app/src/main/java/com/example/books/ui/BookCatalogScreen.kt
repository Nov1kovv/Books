package com.example.books.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BookCatalogScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Каталог книг", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = "",
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("search") },
            placeholder = { Text("Нажмите, чтобы искать книги") }
        )
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar()
    }
}
//@Composable
//fun BookCatalogScreen(viewModel: BookCatalogViewModel, navController: NavController) {
//    var query by remember { mutableStateOf("") } // query хранит текст введённый в поле поиска
//    val books by viewModel.books.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()// Заполняет весь экран
//            .padding(16.dp),// Внутренний отступ от краёв
//        verticalArrangement = Arrangement.Top // Элементы начинаются сверху
//    ) {
//        Text("Каталог книг", modifier = Modifier.fillMaxWidth())
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextField(
//            value = query,
//            onValueChange = { query = it },//Обновление значения при вводе
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { viewModel.searchBooks(query) },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Искать")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Список книг с изображениями
//        LazyColumn(modifier = Modifier
//            .weight(1f)
//            .fillMaxWidth()) {
//            items(books) { book ->
//                Column(modifier = Modifier.padding(vertical = 8.dp)) {
//                    Text(text = book.title)
//
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    val imageUrl = book.imageUrl
//                    Log.d("BookCatalogScreen", "imageUrl = $imageUrl")
//
//                    if (imageUrl != null) {
//                        AsyncImage(
//                            model = imageUrl,
//                            contentDescription = book.title,
//                            modifier = Modifier
//                                .width(100.dp)
//                                .height(100.dp)
//                                .clickable {
//                                    navController.navigate("detail/${book.id}")
//                                }
//                        )
//                    } else {
//                        Text("Нет изображения")
//                    }
//                }
//            }
//        }
//        BottomNavigationBar()
//    }
//}
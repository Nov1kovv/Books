package com.example.books.ui.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.books.ui.bottombar.BottomNavigationBar

@Composable
fun BookCatalogScreen(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
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
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun PreviewBookCatalogScreen(){
    BookCatalogScreen(null)
}
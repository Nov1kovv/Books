package com.example.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ExaminationScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) { // Если пользователь уже вошёл навигация в каталог книг
            navController.navigate("catalog") {
                popUpTo("examination") { inclusive = true } //чтобы пользователь не зашел назад, надо удалить из стека экран
            }
        } else {  // Если пользователь не вошёл отправляем на экран логина
            navController.navigate("login") {
                popUpTo("examination") { inclusive = true } //тоже самое как и с каталогом
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
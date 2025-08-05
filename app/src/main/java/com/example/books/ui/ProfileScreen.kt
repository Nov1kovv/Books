package com.example.books.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Профиль")

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            FirebaseAuth.getInstance().signOut() // Выход из аккаунта
            navController.navigate("login") {
                popUpTo("catalog") { inclusive = true } // Очищаем backstack
            }
        }) {
            Text("Выйти из аккаунта")
        }
    }
}
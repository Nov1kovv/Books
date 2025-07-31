package com.example.books.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {

    val login by viewModel.login.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val isLoading by viewModel.loading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = login,
            onValueChange = { viewModel.onLoginChange(it) },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signInWithEmailAndPassword(login, password) {
                    navController.navigate("catalog")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Загрузка..." else "Войти")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
    Spacer(modifier = Modifier.height(24.dp))

    Row( //горизонтальный контейнер
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center //выравнивание по центру
    ) {
        Text(text = "New user?")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Sign up",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navController.navigate("register")
            }
        )
    }
}

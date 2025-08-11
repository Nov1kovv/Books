package com.example.books.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import org.orbitmvi.orbit.compose.collectSideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.books.ui.registration.mvi.RegistrationAction
import com.example.books.ui.registration.mvi.RegistrationSideEffect
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {

    val state = viewModel.collectAsState().value

    viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is RegistrationSideEffect.NavigateToCatalog -> {
                    navController.navigate("catalog")
                }
                is RegistrationSideEffect.ShowError -> {
                }
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.login,
            onValueChange = { viewModel.dispatch(RegistrationAction.UpdateLogin(it)) },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = { viewModel.dispatch(RegistrationAction.UpdatePassword(it)) },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.dispatch(RegistrationAction.SubmitLogin) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Загрузка..." else "Войти")
        }

        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row( //горизонтальный контейнер
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center //выравнивание по центру
        ) {
            Text(text = "Новый пользователь?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Зарегистрироваться",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate("signup")
                }
            )
        }
    }
}


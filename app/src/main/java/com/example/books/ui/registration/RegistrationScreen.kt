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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.orbitmvi.orbit.compose.collectSideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val backgroundColor = Color(0xFF121212)
    val cardColor = Color(0xFF1E1E1E)
    val textPrimary = Color.White
    val textSecondary = Color(0xFFB0B0B0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Вход",
            color = textPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.login,
            onValueChange = { viewModel.dispatch(RegistrationAction.UpdateLogin(it)) },
            label = { Text("Логин",color = textSecondary) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedTextColor = textPrimary,
                unfocusedTextColor = textPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = { viewModel.dispatch(RegistrationAction.UpdatePassword(it)) },
            label = { Text("Пароль",color = textSecondary) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedTextColor = textPrimary,
                unfocusedTextColor = textPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.dispatch(RegistrationAction.SubmitLogin) },
            modifier = Modifier.fillMaxWidth()
            .height(50.dp),
            shape = RoundedCornerShape(12.dp),
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
            Text(text = "Новый пользователь?", color = textSecondary)
            Spacer(modifier = Modifier.width(6.dp))
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


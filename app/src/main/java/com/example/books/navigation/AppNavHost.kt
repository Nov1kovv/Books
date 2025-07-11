package com.example.books.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.books.RegistrationScreen
import com.example.books.BookCatalogScreen
import com.example.books.RegistrationViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    // NavHost это контейнер, который отображает текущий экран в зависимости от навигационного состояния.
    // navController контроллер, который управляет переходами между экранами.
    // startDestination экран, который будет показан первым при запуске приложения.
    NavHost(navController = navController, startDestination = "register") {
        composable("register") {
            val viewModel = viewModel<RegistrationViewModel>()
            RegistrationScreen(navController, viewModel)
        }
        composable("catalog") { BookCatalogScreen() }
    }
}
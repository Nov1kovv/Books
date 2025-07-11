package com.example.books.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.books.RegistrationScreen
import com.example.books.BookCatalogScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    // NavHost это контейнер, который отображает текущий экран в зависимости от навигационного состояния.
    // navController контроллер, который управляет переходами между экранами.
    // startDestination экран, который будет показан первым при запуске приложения.
    NavHost(navController = navController, startDestination = "register") {
        composable("register") { RegistrationScreen(navController) }
        composable("catalog") { BookCatalogScreen() }
    }
}
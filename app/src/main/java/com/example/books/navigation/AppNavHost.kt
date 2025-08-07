package com.example.books.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.books.ExaminationScreen
import com.example.books.ui.signup.SignUpScreen
import com.example.books.ui.registration.RegistrationScreen
import com.example.books.ui.catalog.BookCatalogScreen
import com.example.books.ui.catalog.BookCatalogViewModel
import com.example.books.ui.bottombar.ProfileScreen
import com.example.books.ui.details.BookDetailScreen
import com.example.books.ui.registration.RegistrationViewModel
import com.example.books.ui.search.SearchScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val catalogViewModel: BookCatalogViewModel = getViewModel()
    // NavHost это контейнер, который отображает текущий экран в зависимости от навигационного состояния.
    // navController контроллер, который управляет переходами между экранами.
    // startDestination экран, который будет показан первым при запуске приложения.
    NavHost(navController = navController, startDestination = "examination") {
        composable("examination") {
            ExaminationScreen(navController)
        }
        composable("login") {
            val viewModel = viewModel<RegistrationViewModel>()
            RegistrationScreen(navController, viewModel)
        }
        composable("signup") {
            val viewModel = viewModel<RegistrationViewModel>()
            SignUpScreen(navController, viewModel)
        }
        composable("catalog") {
            BookCatalogScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("search") {
            SearchScreen(viewModel = catalogViewModel, navController = navController)
        }
        composable("detail/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(bookId = bookId, viewModel = catalogViewModel)
        }
    }
}
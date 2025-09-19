package com.example.books.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.books.ExaminationScreen
import com.example.books.ui.bottombar.favorite.FavoriteScreen
import com.example.books.ui.bottombar.favorite.FavoriteViewModel
import com.example.books.authorization.signup.SignUpScreen
import com.example.books.authorization.registration.RegistrationScreen
import com.example.books.ui.catalog.BookCatalogScreen
import com.example.books.ui.catalog.BookCatalogViewModel
import com.example.books.ui.bottombar.profile.ProfileScreen
import com.example.books.ui.bottombar.profile.ProfileViewModel
import com.example.books.ui.details.BookDetailScreen
import com.example.books.ui.details.BookDetailViewModel
import com.example.books.authorization.registration.RegistrationViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavHost(navController: NavHostController) {
    val catalogViewModel: BookCatalogViewModel = getViewModel()
    val favoriteViewModel: FavoriteViewModel = getViewModel()
    // NavHost это контейнер, который отображает текущий экран в зависимости от навигационного состояния.
    // navController контроллер, который управляет переходами между экранами.
    // startDestination экран, который будет показан первым при запуске приложения.
    NavHost(navController = navController, startDestination = "examination") {
        composable("examination") {
            ExaminationScreen(navController)
        }
        composable("login") {
            val viewModel: RegistrationViewModel = getViewModel()
            RegistrationScreen(navController, viewModel)
        }
        composable("signup") {
            val viewModel: RegistrationViewModel = getViewModel()
            SignUpScreen(navController, viewModel)
        }
        composable("catalog") {
            BookCatalogScreen(navController = navController, viewModel = catalogViewModel)
        }
        composable("profile") {
            val viewModel = getViewModel<ProfileViewModel>()
            ProfileScreen(navController = navController, viewModel = viewModel)
        }
        composable("favorites") {
            FavoriteScreen(navController = navController, viewModel = favoriteViewModel)
        }
        composable("detail/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            val detailViewModel: BookDetailViewModel = getViewModel()
            BookDetailScreen(viewModel = detailViewModel, bookId = bookId)
        }
    }
}

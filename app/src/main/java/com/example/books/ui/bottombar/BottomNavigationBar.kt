package com.example.books.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFF1C1C1C)
    ) {
        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.White,
            indicatorColor = Color(0xFF2A2A2A) // подсветка выбранного пункта
        )

        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("catalog") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Главная") },
            label = { Text("Главная") },
            colors = itemColors
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("favorites") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Избранное") },
            label = { Text("Избранное") },
            colors = itemColors
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Профиль") },
            label = { Text("Профиль") },
            colors = itemColors
        )

    }
}
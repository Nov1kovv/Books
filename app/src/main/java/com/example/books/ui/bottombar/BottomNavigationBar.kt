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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.books.R

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
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.nav_home)) },
            label = { Text(stringResource(R.string.nav_home)) },
            colors = itemColors
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("favorites") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = stringResource(R.string.nav_favorites)) },
            label = { Text(stringResource(R.string.nav_favorites)) },
            colors = itemColors
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = stringResource(R.string.nav_profile)) },
            label = { Text(stringResource(R.string.nav_profile)) },
            colors = itemColors
        )

    }
}
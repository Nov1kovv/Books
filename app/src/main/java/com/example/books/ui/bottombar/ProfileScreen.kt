package com.example.books.ui.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val backgroundColor = Color(0xFF121212) // основной фон
    val cardColor = Color(0xFF1E1E1E) // фон карточек
    val textPrimary = Color.White

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp)
            .background(backgroundColor)
    ){
        Text(
            text = "Профиль",
            color = textPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(cardColor)
                .clickable {
                    viewModel.signOut() {
                        navController.navigate("login")
                    }
                }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Выйти из аккаунта", color = textPrimary, fontSize = 16.sp)
        }
    }
    }
}


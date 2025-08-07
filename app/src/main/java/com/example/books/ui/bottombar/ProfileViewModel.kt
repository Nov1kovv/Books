package com.example.books.ui.bottombar

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signOut(onSignedOut: () -> Unit) {
        auth.signOut()
        onSignedOut()
    }
}
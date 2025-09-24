package com.example.books.ui.bottombar.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthorizationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel( private val authorizationRepository: AuthorizationRepository) : ViewModel() {
    // TODO: Вся бизнес логика через репозиторий

    fun signOut(onSignedOut: () -> Unit) {
        viewModelScope.launch {
            authorizationRepository.signOut() // suspend
            onSignedOut()
        }
    }
}
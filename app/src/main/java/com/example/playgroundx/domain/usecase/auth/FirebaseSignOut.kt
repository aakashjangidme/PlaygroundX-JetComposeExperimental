package com.example.playgroundx.domain.usecase.auth

import com.example.playgroundx.domain.repository.AuthRepository
import javax.inject.Inject

class FirebaseSignOut @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.firebaseSignOut()
}
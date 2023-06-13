package com.example.playgroundx.domain.usecase.auth

import com.example.playgroundx.domain.repository.AuthRepository
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, userName: String) =
        repository.firebaseSignUp(email, password, userName)
}
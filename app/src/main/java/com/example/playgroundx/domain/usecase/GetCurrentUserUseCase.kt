package com.example.playgroundx.domain.usecase

import com.example.playgroundx.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? {
        return repository.currentUser;
    }
}
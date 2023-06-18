package com.example.playgroundx.domain.usecase.auth

import com.example.playgroundx.domain.repository.AuthRepository
import javax.inject.Inject

class CurrentAuthUser @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() =
        repository.currentUser
}
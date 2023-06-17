package com.example.playgroundx.domain.usecase.auth

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseSignOut @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        emit(repository.signOut())
    }
}
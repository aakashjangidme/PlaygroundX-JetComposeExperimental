package com.example.playgroundx.domain.usecase.auth

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, userName: String) = flow {
        emit(Resource.Loading())
        emit(repository.createUserWithEmailAndPassword(email, password, userName))
    }
}
package com.example.playgroundx.domain.usecase.auth

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        emit(repository.signInWithEmailAndPassword(email, password))
    }
}
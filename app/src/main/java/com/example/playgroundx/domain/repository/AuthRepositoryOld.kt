package com.example.playgroundx.domain.repository

import com.example.playgroundx.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface AuthRepositoryOld {

    val currentUser: FirebaseUser?

    val currentUserId: String

    val isUserAuthenticatedInFirebase: Boolean

    suspend fun getFirebaseAuthState(): Flow<Boolean>

    suspend fun firebaseSignIn(email: String, password: String): Flow<Resource<Boolean>>

    suspend fun firebaseSignOut(): Flow<Resource<Boolean>>

    suspend fun firebaseSignUp(
        email: String, password: String, userName: String,
    ): Flow<Resource<Boolean>>
}
package com.example.playgroundx.domain.repository

import com.example.playgroundx.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    val currentUser: FirebaseUser?

    val currentUserId: String

    val isUserAuthenticatedInFirebase: Boolean

    suspend fun currentUserAuthState(): Flow<Boolean>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Resource<Boolean>

    suspend fun signOut(): Resource<Boolean>

    suspend fun createUserWithEmailAndPassword(
        email: String, password: String, userName: String,
    ): Resource<Boolean>
}
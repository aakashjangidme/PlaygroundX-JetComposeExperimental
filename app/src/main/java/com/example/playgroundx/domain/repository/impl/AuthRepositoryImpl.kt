package com.example.playgroundx.domain.repository.impl

import com.example.playgroundx.common.Constant
import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.model.AppUser
import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {
    var operationSuccessful: Boolean = false

    override val currentUser: FirebaseUser?
        get() = auth.currentUser
    override val currentUserId: String
        get() = auth.currentUser!!.uid

    override val isUserAuthenticatedInFirebase
        get() = auth.currentUser != null


    @ExperimentalCoroutinesApi
    override suspend fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun firebaseSignIn(email: String, password: String): Flow<Resource<Boolean>> =
        flow {

            Timber.tag("AuthRepositoryImpl::Dispatchers.IO")
                .d("Current thread: ${Thread.currentThread().name}")

            operationSuccessful = false

            try {
                emit(Resource.Loading())
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    operationSuccessful = true
                }.await()
                emit(Resource.Success(operationSuccessful))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An Unexpected Error"))
                throw e
            }

        }.flowOn(Dispatchers.IO)


    override suspend fun firebaseSignOut(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            auth.signOut()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun firebaseSignUp(
        email: String,
        password: String,
        userName: String,
    ): Flow<Resource<Boolean>> = flow {
        operationSuccessful = false

        Timber.tag("AuthRepositoryImpl::Dispatchers.IO")
            .d("Current thread: ${Thread.currentThread().name}")

        try {
            emit(Resource.Loading())
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessful = true
            }.await()

            if (operationSuccessful) {
                val userid = auth.currentUser?.uid!!
                val obj = AppUser(
                    userName = userName, userid = userid, password = password, email = email
                )
                firestore.collection(Constant.COLLECTION_NAME_USERS).document(userid).set(obj)
                    .addOnSuccessListener {}
                emit(Resource.Success(operationSuccessful))
            } else {
                emit(Resource.Error("Something went wrong while creating the user."))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)
}
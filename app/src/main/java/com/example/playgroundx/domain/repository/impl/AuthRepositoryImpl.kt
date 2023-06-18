package com.example.playgroundx.domain.repository.impl

import com.example.playgroundx.common.Constant
import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.model.AppUser
import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.util.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser
    override val currentUserId: String
        get() = auth.currentUser!!.uid

    override val isUserAuthenticatedInFirebase
        get() = auth.currentUser != null


    @ExperimentalCoroutinesApi
    override suspend fun currentUserAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Resource<Boolean> {
        return safeCall {
            val authRes = withContext(Dispatchers.IO) {
                auth.signInWithEmailAndPassword(email, password).await()
            }

            Resource.Success(authRes.user!!.uid.isNotBlank())
        }
    }

    override suspend fun signOut(): Resource<Boolean> {
        return safeCall {
            auth.signOut()
            Resource.Success(true)
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        userName: String,
    ): Resource<Boolean> {
        return safeCall {

            val authRes = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(email, password).await()
            }

            val userid = authRes.user!!.uid

            val obj = AppUser(
                userName = userName, userid = userid, password = password, email = email
            )

            withContext(Dispatchers.IO) {
                firestore.collection(Constant.COLLECTION_NAME_USERS).document(userid).set(obj)
                    .await()
            }

            Resource.Success(true)
        }
    }
}

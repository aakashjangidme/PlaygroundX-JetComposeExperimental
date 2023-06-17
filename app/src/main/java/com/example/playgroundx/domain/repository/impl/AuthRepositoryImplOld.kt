
package com.example.playgroundx.domain.repository.impl
/*
import com.example.playgroundx.common.Constant
import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.model.AppUser
import com.example.playgroundx.domain.repository.AuthRepository
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
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImplOld @Inject constructor(
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

            Timber.tag("firebaseSignIn").d("Current thread: ${Thread.currentThread().toString()}")
            Timber.tag("firebaseSignIn").d("Current thread name: ${Thread.currentThread().name}")
            Timber.tag("firebaseSignIn").d("Current thread state: ${Thread.currentThread().state}")
            Timber.tag("firebaseSignIn")
                .d("Current thread threadGroup: ${Thread.currentThread().threadGroup}")


            try {
                emit(Resource.Loading())

                val authRes = auth.signInWithEmailAndPassword(email, password).await()

                if (authRes.user != null) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Error("Something went wrong while signing in the user."))
                    throw Error("Something went wrong while signing in the user.")
                }

            } catch (e: Exception) {
                Timber.tag("firebaseSignIn").e(e)
                emit(Resource.Error(e.message ?: "An Unexpected Error"))
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


*/
/*

override suspend fun firebaseSignIn(email: String, password: String): Flow<Resource<Boolean>> =
    flow {

        Timber.tag("firebaseSignIn").d("Current thread: ${Thread.currentThread().toString()}")
        Timber.tag("firebaseSignIn").d("Current thread name: ${Thread.currentThread().name}")
        Timber.tag("firebaseSignIn").d("Current thread state: ${Thread.currentThread().state}")
        Timber.tag("firebaseSignIn")
            .d("Current thread threadGroup: ${Thread.currentThread().threadGroup}")


        try {
            emit(Resource.Loading())

            val authRes = auth.signInWithEmailAndPassword(email, password).await()

            if (authRes.user != null) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error("Something went wrong while signing in the user."))
                throw Error("Something went wrong while signing in the user.")
            }

        } catch (e: Exception) {
            Timber.tag("firebaseSignIn").e(e)
            emit(Resource.Error(e.message ?: "An Unexpected Error"))
        }

    }.flowOn(Dispatchers.IO)
*/
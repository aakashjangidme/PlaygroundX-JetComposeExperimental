package com.example.playgroundx.domain.repository

import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.core.util.await
import com.example.playgroundx.domain.model.AppUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : UserRepository {

    private var operationSuccessful = false


    override suspend fun getUserDetails(userid: String): Flow<Resource<AppUser>> = callbackFlow {

        Resource.Loading<AppUser>()

        val snapShotListener = firebaseFirestore.collection("users").document(userid)
            .addSnapshotListener { snapshot, error ->
                val Resource = if (snapshot != null) {
                    val userInfo = snapshot.toObject(AppUser::class.java)
                    Resource.Success<AppUser>(userInfo!!)
                } else {
                    Resource.Error(error?.message ?: error.toString())
                }
                trySend(Resource).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override suspend fun setUserDetails(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String,
    ): Flow<Resource<Boolean>> = flow {
        operationSuccessful = false
        try {
            val userObj = mutableMapOf<String, String>()
            userObj["name"] = name
            userObj["userName"] = userName
            userObj["bio"] = bio
            userObj["websiteUrl"] = websiteUrl
            firebaseFirestore.collection("users").document(userid)
                .update(userObj as Map<String, Any>).addOnSuccessListener {
                    operationSuccessful = true
                }.await()

            if (operationSuccessful) {
                emit(Resource.Success(operationSuccessful))
            } else {
                emit(Resource.Error("Edit Did Not Succeed"))
            }
        } catch (e: Exception) {

            emit(Resource.Error(e.localizedMessage ?: "An Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)

}
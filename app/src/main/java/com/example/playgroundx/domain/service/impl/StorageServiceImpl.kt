package com.example.playgroundx.domain.service.impl

import android.net.Uri
import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.domain.service.StorageService
import com.example.playgroundx.util.await
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage, auth: AuthRepository,
) : StorageService {


    val userId: String = auth.currentUserId.toString()

    override suspend fun uploadProfilePicture(userId: String, imageUri: Uri): String? {

        val childName = "profile_pictures/${userId}/profile_picture.jpg"

        val storageRef: StorageReference =
            firebaseStorage.reference.child(childName)

        return try {
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.await()

            val downloadUrl = storageRef.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            // Handle any errors during the upload process
            throw e
        }
    }
}
package com.example.playgroundx.domain.service

import android.net.Uri

interface StorageService {

    suspend fun uploadProfilePicture(userId: String, imageUri: Uri): String?
}
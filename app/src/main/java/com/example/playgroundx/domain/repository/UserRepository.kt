package com.example.playgroundx.domain.repository

import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.domain.model.AppUser

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserDetails(userid: String): Flow<Resource<AppUser>>
    suspend fun setUserDetails(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String,
    ): Flow<Resource<Boolean>>
}
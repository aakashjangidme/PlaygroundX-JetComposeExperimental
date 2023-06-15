package com.example.playgroundx.domain.repository

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getCachedUsers(): List<User>

    suspend fun refreshCachedUsers()

    fun getUsers(): Flow<Resource<List<User>>>
}
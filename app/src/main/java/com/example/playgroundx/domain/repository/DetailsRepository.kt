package com.example.playgroundx.domain.repository

import com.example.playgroundx.domain.model.Details

interface DetailsRepository {

    suspend fun getCachedUserDetails(user: String): Details

    suspend fun refreshCacheDetails(user: String)

}
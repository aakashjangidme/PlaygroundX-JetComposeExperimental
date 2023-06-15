package com.example.playgroundx.domain.repository.impl

import com.example.playgroundx.data.local.AppDatabase
import com.example.playgroundx.data.remote.DetailsApi
import com.example.playgroundx.data.toLocal
import com.example.playgroundx.data.toNetwork
import com.example.playgroundx.domain.model.Details
import com.example.playgroundx.domain.repository.DetailsRepository
import timber.log.Timber
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi, private val database: AppDatabase
) : DetailsRepository {

    override suspend fun getCachedUserDetails(user: String): Details {
        return database.userDao.getDetails(user)?.toNetwork() ?: Details();
    }

    override suspend fun refreshCacheDetails(user: String) {
        try {
            val userDetails = detailsApi.getDetails(user)
            database.userDao.insertDetails(userDetails.toLocal())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}
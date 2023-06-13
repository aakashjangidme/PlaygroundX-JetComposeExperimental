package com.example.playgroundx.domain.usecase

import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.domain.model.Details
import com.example.playgroundx.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: DetailsRepository
) {
    //only one public method -> invoke
    operator fun invoke(user: String): Flow<Resource<Details>> = flow {
        emit(Resource.Loading())

        val cachedDetails = repository.getCachedUserDetails(user)

        try {
            // Emit the cached user detail but while loading
            emit(Resource.Loading(cachedDetails))

            repository.refreshCacheDetails(user)

            val details = repository.getCachedUserDetails(user)

            emit(Resource.Success(details))

        } catch (
            e: HttpException
        ) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred.",
                    cachedDetails
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection.",
                    cachedDetails
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Something went wrong.", cachedDetails
                )
            )
        }
    }
}
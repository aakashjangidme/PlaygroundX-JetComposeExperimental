package com.example.playgroundx.domain.usecase.userUseCases

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetUserProfilePicture @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(
        userid: String,
        imageUrl: String,
    ) = flow {

        emit(Resource.Loading())
        emit(
            repository.setUserProfilePicture(
                userid, imageUrl
            )
        )
    }
}
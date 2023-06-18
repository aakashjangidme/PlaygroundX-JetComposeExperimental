package com.example.playgroundx.domain.usecase.userUseCases

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String,
        phoneNumber: String,
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        val setUserDetails = repository.setUserDetails(
            userid = userid,
            name = name,
            userName = userName,
            bio = bio,
            websiteUrl = websiteUrl,
            phoneNumber = phoneNumber
        )
        emit(setUserDetails)
    }

}

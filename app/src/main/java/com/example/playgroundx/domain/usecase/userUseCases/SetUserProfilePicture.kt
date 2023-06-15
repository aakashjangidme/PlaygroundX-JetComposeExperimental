package com.example.playgroundx.domain.usecase.userUseCases

import com.example.playgroundx.domain.repository.UserRepository
import javax.inject.Inject

class SetUserProfilePicture @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(
        userid: String,
        imageUrl: String,
    ) = repository.setUserProfilePicture(
        userid = userid,
        imageUrl = imageUrl
    )
}
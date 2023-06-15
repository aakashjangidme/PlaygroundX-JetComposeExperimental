package com.example.playgroundx.domain.usecase.userUseCases

import com.example.playgroundx.domain.repository.UserRepository
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String,
        phoneNumber: String,
    ) = repository.setUserDetails(
        userid = userid,
        name = name,
        userName = userName,
        bio = bio,
        websiteUrl = websiteUrl,
        phoneNumber = phoneNumber
    )
}
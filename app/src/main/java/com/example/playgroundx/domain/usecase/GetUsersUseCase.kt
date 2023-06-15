package com.example.playgroundx.domain.usecase

import com.example.playgroundx.common.Resource
import com.example.playgroundx.domain.model.User
import com.example.playgroundx.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    //only one public method -> invoke
    operator fun invoke(): Flow<Resource<List<User>>> {
        return repository.getUsers()
    }
}
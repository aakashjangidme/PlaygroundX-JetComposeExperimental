package com.example.playgroundx.domain.repository


import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.data.local.AppDatabase
import com.example.playgroundx.data.remote.UsersApi
import com.example.playgroundx.data.toLocal
import com.example.playgroundx.data.toNetwork
import com.example.playgroundx.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi, private val database: AppDatabase
) : UsersRepository {

    private val _getCachedUsers = database.userDao.observeAllUsers().map { it.toNetwork() }

    override fun getUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())

        val cachedUsers = _getCachedUsers.firstOrNull() ?: emptyList()

        // Emit the cached users
        emit(Resource.Loading(cachedUsers))

        try {
            val users = usersApi.getUsers()
            database.userDao.insertUsers(users.toLocal())

            emit(Resource.Success(users.toNetwork()))

        } catch (e: IOException) {
            e.printStackTrace();
            emit(
                Resource.Error(
                    "Couldn't reach the server. Check your internet connection.", cachedUsers
                )
            )
        } catch (e: HttpException) {
            e.printStackTrace();
            emit(Resource.Error("An unexpected error occurred.", cachedUsers))
        } catch (e: Exception) {
            e.printStackTrace();
            emit(Resource.Error("Something went wrong.", cachedUsers))
        }
    }.catch { e ->
        e.printStackTrace();
        emit(Resource.Error("Something went wrong.", emptyList()))
    }


    override suspend fun getCachedUsers(): List<User> {
        return _getCachedUsers.firstOrNull() ?: emptyList();
    }

    override suspend fun refreshCachedUsers() {
        val users = usersApi.getUsers()
        database.userDao.insertUsers(users.toLocal())
    }

}
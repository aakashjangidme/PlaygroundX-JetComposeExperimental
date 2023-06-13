package com.example.playgroundx.data.local


import androidx.room.*
import com.example.playgroundx.data.local.entity.DetailsEntity
import com.example.playgroundx.data.local.entity.UserEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity")
    fun observeAllUsers(): Flow<List<UserEntity>>

    @Query("select * from UserEntity")
    suspend fun getUsers(): List<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("select * from DetailsEntity WHERE user LIKE :user")
    suspend fun getDetails(user: String): DetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(detailsEntity: DetailsEntity)

    @Upsert
    suspend fun upsert(user: UserEntity)

    @Upsert
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAllUsers()
}


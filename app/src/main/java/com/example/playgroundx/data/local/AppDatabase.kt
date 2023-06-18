package com.example.playgroundx.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playgroundx.data.local.dao.UserDao
import com.example.playgroundx.data.local.entity.DetailsEntity
import com.example.playgroundx.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, DetailsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
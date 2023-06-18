package com.example.playgroundx.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playgroundx.data.local.dao.PasswordEntryDao
import com.example.playgroundx.data.local.dao.UserDao
import com.example.playgroundx.data.local.entity.DetailsEntity
import com.example.playgroundx.data.local.entity.PasswordEntity
import com.example.playgroundx.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, DetailsEntity::class, PasswordEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val passwordEntryDao: PasswordEntryDao
}
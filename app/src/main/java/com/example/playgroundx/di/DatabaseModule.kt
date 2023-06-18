package com.example.playgroundx.di

import android.content.Context
import androidx.room.Room
import com.example.playgroundx.data.local.AppDatabase
import com.example.playgroundx.data.local.dao.PasswordEntryDao
import com.example.playgroundx.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "playgroundx.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(database: AppDatabase): UserDao = database.userDao

    @Provides
    @Singleton
    fun providePasswordEntryDao(database: AppDatabase): PasswordEntryDao = database.passwordEntryDao


}
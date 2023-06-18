package com.example.playgroundx.di

import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.domain.repository.UserRepository
import com.example.playgroundx.domain.repository.impl.AuthRepositoryImpl
import com.example.playgroundx.domain.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}
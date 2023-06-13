package com.example.playgroundx.di

import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.domain.repository.AuthRepositoryImpl
import com.example.playgroundx.domain.repository.DetailsRepository
import com.example.playgroundx.domain.repository.DetailsRepositoryImpl
import com.example.playgroundx.domain.repository.UsersRepository
import com.example.playgroundx.domain.repository.UsersRepositoryImpl
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
    abstract fun bindUserRepository(impl: UsersRepositoryImpl): UsersRepository

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(impl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}
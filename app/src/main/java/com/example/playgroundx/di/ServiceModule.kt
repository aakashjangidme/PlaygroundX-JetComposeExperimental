package com.example.playgroundx.di

import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.service.StorageService
import com.example.playgroundx.domain.service.impl.LogServiceImpl
import com.example.playgroundx.domain.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
//    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    @Singleton
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    @Singleton
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

//    @Binds
//    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}

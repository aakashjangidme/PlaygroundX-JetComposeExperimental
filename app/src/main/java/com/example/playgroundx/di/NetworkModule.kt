package com.example.playgroundx.di


import com.example.playgroundx.BuildConfig
import com.example.playgroundx.core.common.Constant.BASE_URL
import com.example.playgroundx.data.remote.DetailsApi
import com.example.playgroundx.data.remote.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(Duration.ofSeconds(5)).readTimeout(Duration.ofSeconds(5))
            .callTimeout(Duration.ofSeconds(5)).build()
    } else {
        OkHttpClient.Builder().connectTimeout(Duration.ofSeconds(5))
            .readTimeout(Duration.ofSeconds(5)).callTimeout(Duration.ofSeconds(5)).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideUsersApiService(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideUserDetailsApiService(retrofit: Retrofit): DetailsApi =
        retrofit.create(DetailsApi::class.java)

}
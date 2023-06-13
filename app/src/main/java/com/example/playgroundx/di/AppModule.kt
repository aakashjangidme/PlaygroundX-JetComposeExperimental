package com.example.playgroundx.di

import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import com.example.playgroundx.domain.usecase.auth.CurrentAuthUser
import com.example.playgroundx.domain.usecase.auth.FirebaseAuthState
import com.example.playgroundx.domain.usecase.auth.FirebaseSignIn
import com.example.playgroundx.domain.usecase.auth.FirebaseSignOut
import com.example.playgroundx.domain.usecase.auth.FirebaseSignUp
import com.example.playgroundx.domain.usecase.auth.IsUserAuthenticated
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCase(
        isUserAuthenticated = IsUserAuthenticated(repository = repository),
        firebaseAuthState = FirebaseAuthState(repository = repository),
        firebaseSignOut = FirebaseSignOut(repository = repository),
        firebaseSignIn = FirebaseSignIn(repository = repository),
        firebaseSignUp = FirebaseSignUp(repository = repository),
        currentAuthUser = CurrentAuthUser(repository = repository)
    )

}


package com.example.playgroundx.di

import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.domain.repository.UserRepository
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import com.example.playgroundx.domain.usecase.auth.CurrentAuthUser
import com.example.playgroundx.domain.usecase.auth.FirebaseAuthState
import com.example.playgroundx.domain.usecase.auth.FirebaseSignIn
import com.example.playgroundx.domain.usecase.auth.FirebaseSignOut
import com.example.playgroundx.domain.usecase.auth.FirebaseSignUp
import com.example.playgroundx.domain.usecase.auth.IsUserAuthenticated
import com.example.playgroundx.domain.usecase.userUseCases.GetUserDetails
import com.example.playgroundx.domain.usecase.userUseCases.SetUserDetails
import com.example.playgroundx.domain.usecase.userUseCases.SetUserProfilePicture
import com.example.playgroundx.domain.usecase.userUseCases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

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
        currentUserAuthState = FirebaseAuthState(repository = repository),
        signOut = FirebaseSignOut(repository = repository),
        signInWithEmailAndPassword = FirebaseSignIn(repository = repository),
        createUserWithEmailAndPassword = FirebaseSignUp(repository = repository),
        currentAuthUser = CurrentAuthUser(repository = repository)
    )

    @Singleton
    @Provides
    fun provideUserUseCases(repository: UserRepository) = UserUseCases(
        getUserDetails = GetUserDetails(repository = repository),
        setUserDetails = SetUserDetails(repository = repository),
        setUserProfilePicture = SetUserProfilePicture(repository = repository)
    )

}


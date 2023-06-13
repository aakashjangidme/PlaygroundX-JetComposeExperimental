package com.example.playgroundx.domain.usecase.auth

data class AuthUseCase(
    val isUserAuthenticated: IsUserAuthenticated,
    val firebaseAuthState: FirebaseAuthState,
    val firebaseSignIn: FirebaseSignIn,
    val firebaseSignOut: FirebaseSignOut,
    val firebaseSignUp: FirebaseSignUp,
    val currentAuthUser: CurrentAuthUser
)
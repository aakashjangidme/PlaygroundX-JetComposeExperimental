package com.example.playgroundx.domain.usecase.auth

data class AuthUseCase(
    val isUserAuthenticated: IsUserAuthenticated,
    val currentUserAuthState: FirebaseAuthState,
    val signInWithEmailAndPassword: FirebaseSignIn,
    val signOut: FirebaseSignOut,
    val createUserWithEmailAndPassword: FirebaseSignUp,
    val currentAuthUser: CurrentAuthUser,
)
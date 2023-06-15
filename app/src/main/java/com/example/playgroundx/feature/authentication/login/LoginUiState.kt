package com.example.playgroundx.feature.authentication.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val email: String = "",
    val password: String = "",
)

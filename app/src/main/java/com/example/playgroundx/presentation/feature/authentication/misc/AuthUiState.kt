package com.example.playgroundx.presentation.feature.authentication.misc

sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val errorMessage: String) : AuthUiState()
}
package com.example.playgroundx.presentation.feature.profile


data class ProfileUiState(
    val isLoading: Boolean = false,
    val userid: String = "",
    val name: String = "",
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val imageUrl: String = "",
    val phoneNumber: String = "",
    val bio: String = "",
)

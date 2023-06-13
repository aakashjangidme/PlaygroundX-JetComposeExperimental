package com.example.playgroundx.feature.users

import com.example.playgroundx.domain.model.User

data class UsersUiState(
    val isLoading: Boolean = false, val list: List<User> = emptyList(), val error: String = ""
)
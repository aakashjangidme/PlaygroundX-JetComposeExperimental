package com.example.playgroundx.feature.authentication.signup

data class SignUpUiState(
  val username: String = "",
  val email: String = "",
  val password: String = "",
  val repeatPassword: String = "",
)

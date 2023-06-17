package com.example.playgroundx.feature.authentication.login

import com.example.playgroundx.common.Resource
import com.example.playgroundx.common.Screens
import com.example.playgroundx.common.ext.isValidEmail
import com.example.playgroundx.common.snackbar.SnackbarManager
import com.example.playgroundx.common.snackbar.SnackbarMessage
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.playgroundx.R.string as AppText


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCase,
    logService: LogService,
) : BaseViewModel(logService) {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(LoginUiState(isLoading = false))

    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Screens.SignUpScreen.route, Screens.LoginScreen.route)
    }


    fun onClickSignIn(openAndPopUp: (String, String) -> Unit) {

        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {

            authUseCases.signInWithEmailAndPassword(email, password).onEach { res ->

                when (res) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        openAndPopUp(Screens.SettingsScreen.route, Screens.LoginScreen.route)
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = res.message.toString())
                        }

                        SnackbarManager.showMessage(SnackbarMessage.StringSnackbar(res.message.toString()))
                    }

                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                }
            }.catch { e -> println("Caught $e") }.collect()
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
//            authUseCases.sendRecoveryEmail(email) //TODO:
            SnackbarManager.showMessage(AppText.recovery_email_sent)

        }
    }
}

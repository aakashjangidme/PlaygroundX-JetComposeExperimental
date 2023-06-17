package com.example.playgroundx.feature.authentication.signup


import com.example.playgroundx.common.Resource
import com.example.playgroundx.common.Screens
import com.example.playgroundx.common.ext.isValidEmail
import com.example.playgroundx.common.ext.isValidPassword
import com.example.playgroundx.common.ext.passwordMatches
import com.example.playgroundx.common.snackbar.SnackbarManager
import com.example.playgroundx.common.snackbar.SnackbarMessage
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.playgroundx.R.string as AppText


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    logService: LogService,
) : BaseViewModel(logService) {


    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(SignUpUiState(isLoading = false))

    val uiState: StateFlow<SignUpUiState>
        get() = _uiState.asStateFlow()


    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val username
        get() = uiState.value.username


    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onRepeatPasswordChange(newValue: String) {
        _uiState.update { it.copy(repeatPassword = newValue) }
    }

    fun onClickSignIn(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Screens.LoginScreen.route, Screens.SignUpScreen.route)
    }

    fun onClickSignUp(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(_uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            authUseCase.createUserWithEmailAndPassword(email, password, username).collect() { res ->
                when (res) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }

                        openAndPopUp(Screens.SettingsScreen.route, Screens.SignUpScreen.route)

                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = res.message.toString())
                        }

                        SnackbarManager.showMessage(SnackbarMessage.StringSnackbar(res.message.toString()))
                    }

                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}

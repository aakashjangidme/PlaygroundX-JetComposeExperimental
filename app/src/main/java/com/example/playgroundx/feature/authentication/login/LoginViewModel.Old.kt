package com.example.playgroundx.feature.authentication.login

/*

import androidx.compose.runtime.mutableStateOf
import com.example.playgroundx.common.Resource
import com.example.playgroundx.common.Screens
import com.example.playgroundx.common.ext.isValidEmail
import com.example.playgroundx.common.snackbar.SnackbarManager
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject
import com.example.playgroundx.R.string as AppText


data class LoginInUiEventsOld(
    val isLoading: Boolean = false,
)

@HiltViewModel
class LoginViewModelOld @Inject constructor(
    private val authUseCases: AuthUseCase,
    logService: LogService,
) : BaseViewModel(logService) {

    var uiState = mutableStateOf(LoginUiState())
        private set

    var uiEvents = MutableStateFlow(LoginInUiEvents())
        private set


    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Screens.SignUpScreen.route, Screens.LoginScreen.route)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            Timber.tag("LoginViewModel::Dispatchers.IO")
                .d("Current thread: ${Thread.currentThread().name}")

            authUseCases.firebaseSignIn(email, password).collect() {
                when (it) {

                    is Resource.Success -> {
                        uiEvents.value = uiEvents.value.copy(isLoading = false)
                        openAndPopUp(Screens.SettingsScreen.route, Screens.LoginScreen.route)
                    }

                    is Resource.Error -> {
                        uiEvents.value = uiEvents.value.copy(isLoading = false)
                    }

                    is Resource.Loading -> {
                        uiEvents.value = uiEvents.value.copy(isLoading = true)
                    }
                }
            }
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
*/

package com.example.playgroundx.feature.authentication.signup


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.playgroundx.common.Resource
import com.example.playgroundx.common.Screens
import com.example.playgroundx.common.ext.isValidEmail
import com.example.playgroundx.common.ext.isValidPassword
import com.example.playgroundx.common.ext.passwordMatches
import com.example.playgroundx.common.snackbar.SnackbarManager
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import com.example.playgroundx.R.string as AppText


data class SignUpUiEvents(
    val isLoading: Boolean = false,
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    logService: LogService,
) : BaseViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    var uiEvents = MutableStateFlow(SignUpUiEvents())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val username
        get() = uiState.value.username


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(Screens.LoginScreen.route, Screens.SignUpScreen.route)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            authUseCase.firebaseSignUp(email, password, username).collect() {}
            openAndPopUp(Screens.SettingsScreen.route, Screens.SignUpScreen.route)
        }
    }


    private fun signUpWithFlow() {
        viewModelScope.launch(Dispatchers.IO) {

            Timber.tag("DetailsViewModel::Dispatchers.IO")
                .d("Current thread: ${Thread.currentThread().name}")

            authUseCase.firebaseSignUp(email, password, username).collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Resource.Success -> {
                            uiEvents.value = uiEvents.value.copy(isLoading = false)
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
    }

}

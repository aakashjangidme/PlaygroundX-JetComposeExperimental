package com.example.playgroundx.feature.settings

import androidx.compose.runtime.mutableStateOf
import com.example.playgroundx.common.Screens
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val authUseCase: AuthUseCase,
) : BaseViewModel(logService) {


    var email = mutableStateOf("")
        private set

    private val currentUserEmail
        get() = authUseCase.currentAuthUser()?.email

    init {
        launchCatching {
            email.value = authUseCase.currentAuthUser()?.email.toString()
        }
    }

    fun onClickSignOut(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            authUseCase.signOut().collect()
            openAndPopUp(Screens.SplashScreen.route, Screens.SettingsScreen.route)
        }
    }

    fun onClickProfile(navigate: (String) -> Unit) {
        launchCatching {
            navigate(Screens.ProfileScreen.route)
        }
    }

}
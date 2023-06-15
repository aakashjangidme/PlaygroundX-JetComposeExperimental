package com.example.playgroundx.feature.splash


import androidx.compose.runtime.mutableStateOf
import com.example.playgroundx.common.Screens
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.repository.AuthRepository
import com.example.playgroundx.domain.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
//    configurationService: ConfigurationService,
    private val auth: AuthRepository,
    logService: LogService,
) : BaseViewModel(logService) {

    val showError = mutableStateOf(false)

    init {
        launchCatching {
//            configurationService.fetchConfiguration()
        }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false

        val authValue = auth.isUserAuthenticatedInFirebase

        if (authValue) openAndPopUp(Screens.SettingsScreen.route, Screens.SplashScreen.route)
        else openAndPopUp(Screens.LoginScreen.route, Screens.SplashScreen.route)

    }

    /* private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
         launchCatching(snackbar = false) {
             try {
                 auth.createAnonymousAccount()
             } catch (ex: FirebaseAuthException) {
                 showError.value = true
                 throw ex
             }
             openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
         }
     }*/
}

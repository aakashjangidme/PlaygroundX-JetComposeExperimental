package com.example.playgroundx.core

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.playgroundx.common.Screens
import com.example.playgroundx.presentation.feature.authentication.login.LoginScreen
import com.example.playgroundx.presentation.feature.authentication.signup.SignUpScreen
import com.example.playgroundx.presentation.feature.password_manager.PasswordManagerScreen
import com.example.playgroundx.presentation.feature.profile.ProfileScreen
import com.example.playgroundx.presentation.feature.profile.edit.ProfileEditScreen
import com.example.playgroundx.presentation.feature.settings.SettingsScreen
import com.example.playgroundx.presentation.feature.splash.SplashScreen


fun NavGraphBuilder.playgroundXNavGraph(appState: PlaygroundXAppState) {
    composable(Screens.SplashScreen.route) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screens.LoginScreen.route) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screens.SignUpScreen.route) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screens.SettingsScreen.route) {
        SettingsScreen(navigate = { route ->
            appState.navigate(route)
        }, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screens.ProfileScreen.route) {
        ProfileScreen(navigate = { route ->
            appState.navigate(route)
        })
    }

    composable(Screens.ProfileEditScreen.route) {
        ProfileEditScreen(navigate = { route ->
            appState.navigate(route)
        }, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Screens.PasswordManagerScreen.route) {
        PasswordManagerScreen()
    }

}

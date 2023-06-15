package com.example.playgroundx.core

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.playgroundx.common.Screens
import com.example.playgroundx.feature.authentication.login.LoginScreen
import com.example.playgroundx.feature.authentication.signup.SignUpScreen
import com.example.playgroundx.feature.feed.FeedsScreen
import com.example.playgroundx.feature.profile.ProfileScreen
import com.example.playgroundx.feature.profile.edit.ProfileEditScreen
import com.example.playgroundx.feature.settings.SettingsScreen
import com.example.playgroundx.feature.splash.SplashScreen


object Argument {
    const val USERNAME = "username"
}


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


    composable(Screens.FeedsScreen.route) { backStackEntry ->
        FeedsScreen(appState.navController, onClickSignOut = {
//            authViewModel.signOut();
            if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                appState.navigateAndPopUp(Screens.SplashScreen.route, Screens.FeedsScreen.route)
            }
        }, onClickProfile = { })
    }
} //FeedsScreen


/*composable(SETTINGS_SCREEN) {
    SettingsScreen(
        restartApp = { route -> appState.clearAndNavigate(route) },
        openScreen = { route -> appState.navigate(route) }
    )
}

composable(LOGIN_SCREEN) {
    LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
}

composable(SIGN_UP_SCREEN) {
    SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
}

composable(TASKS_SCREEN) { TasksScreen(openScreen = { route -> appState.navigate(route) }) }

composable(
    route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
    arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
) {
    EditTaskScreen(
        popUpScreen = { appState.popUp() },
        taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID
    )
}*/


/*
    composable(Screens.LoginScreen.route) { backStackEntry ->
        LoginScreen(onLoginSuccessful = {
            // In order to discard duplicated navigation events, we check the Lifecycle
            if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                appState.navigateAndPopUp(Screens.FeedsScreen.route, Screens.LoginScreen.route)
            }
        }, onClickSignUp = {
            if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                appState.navigate(Screens.SignUpScreen.route)
            }
        })
    } //LoginScreen
*/

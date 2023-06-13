package com.example.playgroundx.core

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playgroundx.core.common.Screens
import com.example.playgroundx.feature.feed.FeedsScreen
import com.example.playgroundx.feature.splash.SplashScreen
import com.example.playgroundx.feature.authentication.AuthViewModel
import com.example.playgroundx.feature.authentication.LoginScreen
import com.example.playgroundx.feature.authentication.SignUpScreen
import com.example.playgroundx.feature.profile.ProfileScreen


object Argument {
    const val USERNAME = "username"
}

@Composable
fun PlaygroundXNavHost() {

    val navController = rememberNavController()

    val startDestination: String = Screens.SplashScreen.route

    val authViewModel: AuthViewModel = hiltViewModel();


    NavHost(
        navController = navController, startDestination = startDestination
    ) {

        composable(Screens.SplashScreen.route) { backStackEntry ->
            SplashScreen(navController = navController, authViewModel)
        } //SplashScreen


        composable(Screens.LoginScreen.route) { backStackEntry ->
            LoginScreen(onLoginSuccessful = {
                // In order to discard duplicated navigation events, we check the Lifecycle
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.FeedsScreen.route) {
                        popUpTo(Screens.LoginScreen.route) { inclusive = false }
                    }
                }
            }, onClickSignUp = {
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.SignUpScreen.route)
                }
            })
        } //LoginScreen

        composable(Screens.SignUpScreen.route) { backStackEntry ->
            SignUpScreen(onSignUpSuccessful = {
                // In order to discard duplicated navigation events, we check the Lifecycle
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.FeedsScreen.route) {
                        popUpTo(Screens.SignUpScreen.route) { inclusive = true }
                    }
                }
            }, onClickSignIn = {
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.LoginScreen.route)
                }
            })
        } //SignUpScreen

        composable(Screens.ProfileScreen.route) { backStackEntry ->
            ProfileScreen(onClickSignOut = {
                authViewModel.signOut();
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.SplashScreen.route) {
                        popUpTo(Screens.ProfileScreen.route) { inclusive = false }
                    }
                }
            })
        } //ProfileScreen

        composable(Screens.FeedsScreen.route) { backStackEntry ->
            FeedsScreen(onClickSignOut = {
                authViewModel.signOut();
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.SplashScreen.route) {
                        popUpTo(Screens.FeedsScreen.route) { inclusive = false }
                    }
                }
            }, onClickProfile = {

                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate(Screens.ProfileScreen.route)
                }
            })
        } //FeedsScreen


        /*
                composable(Destinations.ROUTE_USER) { backStackEntry ->
                    //TODO: can implement Route Composable, and then handle ROUTING logic
                    UsersScreen(onUserClick = { username ->
                        // In order to discard duplicated navigation events, we check the Lifecycle
                        if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                            navController.navigate("${Destinations.ROUTE_DETAIL}/$username")
                        }
                    })
                } //UsersScreen

                composable(
                    route = "${Destinations.ROUTE_DETAIL}/{${Argument.USERNAME}}",
                    arguments = listOf(navArgument(Argument.USERNAME) {
                        type = NavType.StringType
                    }),
                ) {
                    DetailsScreen()
                }  //DetailsScreen

                */
    }

}








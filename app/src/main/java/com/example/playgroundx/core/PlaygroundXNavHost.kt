package com.example.playgroundx.core

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playgroundx.feature.details.DetailsScreen
import com.example.playgroundx.feature.users.UsersScreen


object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val USER_ROUTE = "user"
    const val DETAIL_ROUTE = "detail"
}

object Argument {
    const val USERNAME = "username"
}


@Composable
fun PlaygroundXNavHost() {

    val navController = rememberNavController()

    val startDestination: String = Destinations.USER_ROUTE


    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        //UsersScreen
        composable(Destinations.USER_ROUTE) { backStackEntry ->
            //TODO: can implement Route Composable, and then handle ROUTING logic
            UsersScreen(onUserClick = { username ->
                // In order to discard duplicated navigation events, we check the Lifecycle
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.navigate("${Destinations.DETAIL_ROUTE}/$username")
                }
            })
        }
        //DetailsScreen
        composable(
            route = "${Destinations.DETAIL_ROUTE}/{${Argument.USERNAME}}",
            arguments = listOf(navArgument(Argument.USERNAME) {
                type = NavType.StringType
            }),
        ) {
            DetailsScreen()
        }
    }

}









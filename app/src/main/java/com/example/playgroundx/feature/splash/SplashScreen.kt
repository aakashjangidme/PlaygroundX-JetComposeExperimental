package com.example.playgroundx.feature.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.playgroundx.R
import com.example.playgroundx.core.common.Screens
import com.example.playgroundx.feature.authentication.AuthViewModel
import timber.log.Timber

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel) {
    val authValue = authViewModel.isUserAuthenticated
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f, animationSpec = tween(durationMillis = 1500, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )

        Timber.tag("SplashScreen").d(authValue.value.data.toString());
        Timber.tag("SplashScreen").d(authValue.value.message.toString());

        when (authValue.value.data == true) {
            true -> {
                navController.navigate(Screens.FeedsScreen.route) {
                    popUpTo(Screens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            false -> {
                navController.navigate(Screens.LoginScreen.route) {
                    popUpTo(Screens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            else -> {
                navController.navigate(Screens.LoginScreen.route) {
                    popUpTo(Screens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "Splash Screen Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}
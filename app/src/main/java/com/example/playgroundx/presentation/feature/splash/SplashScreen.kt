package com.example.playgroundx.presentation.feature.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playgroundx.util.ext.basicButton
import com.example.playgroundx.common.iconResourceId
import com.example.playgroundx.presentation.composables.BasicButton
import com.example.playgroundx.R.string as AppText


@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val scale = remember {
        Animatable(0f)
    }

    val updatedIconResourceId = rememberUpdatedState(iconResourceId)

    /**
     * This will be launched/executed as soon as the composable is launched (maybe like a init state)?
     */
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f, animationSpec = tween(durationMillis = 1500, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )
        splashViewModel.onAppStart(openAndPopUp)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

            Crossfade(targetState = updatedIconResourceId.value) {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Splash Screen Logo",
                    modifier = Modifier
                        .scale(scale.value)
                        .width(500.dp)
                )
            }

            if (splashViewModel.showError.value) {
                Text(text = stringResource(AppText.generic_error))
                BasicButton(AppText.try_again, Modifier.basicButton()) {
                    splashViewModel.onAppStart(openAndPopUp)
                }
            }

        }
    }
    
}

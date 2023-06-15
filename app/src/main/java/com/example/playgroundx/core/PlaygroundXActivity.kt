package com.example.playgroundx.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.playgroundx.PlaygroundXHiltApp
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


private val TAG: String = PlaygroundXHiltApp::class.java.simpleName

@AndroidEntryPoint
class PlaygroundXActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.tag(TAG).d("PlaygroundXActivity")

        val splashScreen = installSplashScreen()

        val viewModel: PlaygroundXViewModel by viewModels()

        splashScreen.setKeepOnScreenCondition {
            viewModel.isScreenLoading.value!!
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PlaygroundXApp()
        }
    }
}

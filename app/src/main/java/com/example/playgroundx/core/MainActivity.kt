package com.example.playgroundx.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.playgroundx.BaseApplication
import com.example.playgroundx.ui.theme.PlaygroundXJetComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


private val TAG: String = BaseApplication::class.java.simpleName

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("MainActivity")

        val splashScreen = installSplashScreen()

        val viewModel: MainActivityViewModel by viewModels()

        splashScreen.setKeepOnScreenCondition {
            viewModel.isScreenLoading.value!!
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PlaygroundXJetComposeTheme {

                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    PlaygroundXNavHost()
                }
            }
        }
    }
}

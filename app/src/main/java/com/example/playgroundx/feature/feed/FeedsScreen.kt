package com.example.playgroundx.feature.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playgroundx.feature.authentication.AuthViewModel


@Composable
fun FeedsScreen(
    onClickSignOut: () -> Unit,
    onClickProfile: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val signOutState by viewModel.signOutState

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        Button(
            onClick = onClickProfile,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Go to profile", style = MaterialTheme.typography.titleMedium
            )
        }

    }

}
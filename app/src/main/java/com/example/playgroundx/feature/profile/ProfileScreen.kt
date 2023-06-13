package com.example.playgroundx.feature.profile

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
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.feature.authentication.AuthViewModel


@Composable
fun ProfileScreen(
    onClickSignOut: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val signOutState by viewModel.signOutState

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        Button(
            onClick = onClickSignOut,
            enabled = signOutState !is Resource.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Sign Out", style = MaterialTheme.typography.titleMedium
            )
        }

    }
}
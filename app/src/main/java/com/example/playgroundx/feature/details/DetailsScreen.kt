package com.example.playgroundx.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.playgroundx.feature.components.ErrorScreen


@Composable
fun DetailsScreen() {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val uiState = viewModel.state.value

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = uiState.detail.avatar,
                contentDescription = null
            )
            Column {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = uiState.detail.name.orEmpty(),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = uiState.formattedUserSince,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = uiState.detail.location.orEmpty(),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }

        if (uiState.error.isNotBlank()) {
            ErrorScreen(uiState.error)
        } //ErrorScreen

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } //CircularProgressIndicator
    }
}
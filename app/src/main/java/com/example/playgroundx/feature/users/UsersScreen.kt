package com.example.playgroundx.feature.users


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.playgroundx.domain.model.User
import com.example.playgroundx.feature.components.ErrorScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onUserClick: (String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel(),
) {
    val uiState = viewModel.state.value
    val uiEvent = viewModel.eventFlow
    val currentUser = viewModel.currentUser!!


    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    LaunchedEffect(key1 = uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection), snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }, topBar = {
        TopBar(
            currentUser.displayName.toString(), currentUser.photoUrl.toString(), scrollBehavior
        )
    }) {
        Box(
            modifier = Modifier
//                .statusBarsPadding()
                .fillMaxSize()
        ) {
            if (uiState.list.isEmpty()) {
                Text(
                    "Oops, It's empty here.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            } //Text


            LazyColumn {
                items(uiState.list) { item ->
                    UserItem(item = item, onUserClick = onUserClick)
                }
            } //LazyColumn::UserItem

            if (uiState.error.isNotBlank()) {
                ErrorScreen(uiState.error)
            } //ErrorScreen

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } //CircularProgressIndicator
        }
    }

}

@Composable
fun UserItem(item: User, onUserClick: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onUserClick(item.username) }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            modifier = Modifier.size(40.dp), model = item.avatar, contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = item.username,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    avatar: String,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = Modifier.size(40.dp), model = avatar, contentDescription = null
            )

            Text(title)
        }
    }, actions = {
        IconButton(onClick = {/* Do Something*/ }) {
            Icon(Icons.Filled.Settings, null)
        }
    }, scrollBehavior = scrollBehavior)

}
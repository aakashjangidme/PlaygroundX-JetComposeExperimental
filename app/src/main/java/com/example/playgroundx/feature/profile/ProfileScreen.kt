package com.example.playgroundx.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.core.util.ShowToast
import com.example.playgroundx.feature.authentication.AuthViewModel
import com.example.playgroundx.feature.components.BottomNavigationItem
import com.example.playgroundx.feature.components.BottomNavigationMenu
import com.example.playgroundx.feature.components.LoadingIndicator


@Composable
fun ProfileScreen(
    navController: NavController,
    onClickSignOut: () -> Unit,
    viewModel: UserViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val signOutState by authViewModel.signOutState

    when (val response = viewModel.getUserData.value) {
        is Resource.Error -> ShowToast(message = response.message!!)
        is Resource.Loading -> LoadingIndicator()
        is Resource.Success -> {
            if (response.data != null) {
                val user = response.data

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "username : ${user.userName}")
                    Text(text = "name : ${user.name}")
                    Text(text = "email : ${user.email}")

                    BottomNavigationMenu(
                        selectedItem = BottomNavigationItem.PROFILE, navController = navController
                    )
                }
            }
        }

    }
}


/*
            if (userInfo.data != null)
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

                }*/
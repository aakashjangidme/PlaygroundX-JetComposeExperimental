package com.example.playgroundx.feature.authentication

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playgroundx.R
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.core.util.ShowToast
import com.example.playgroundx.feature.components.LoadingIndicator
import timber.log.Timber


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onLoginSuccessful: () -> Unit,
    onClickSignUp: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()


    val authResource by viewModel.signInState

    val mContext = LocalContext.current

    val authUiState = when (authResource) {
        is Resource.Loading -> AuthUiState.Loading
        is Resource.Success -> AuthUiState.Success
        is Resource.Error -> AuthUiState.Error((authResource as Resource.Error).message!!)
        else -> AuthUiState.Initial
    }


    Timber.tag("LoginScreen").d(authResource.data.toString());
    Timber.tag("LoginScreen").d(authResource.message.toString());

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(scrollState)
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }


            Image(
                painter = painterResource(id = R.drawable.android),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
                    .padding(16.dp),
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(id = R.string.email)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = authUiState != AuthUiState.Loading
            ) //emailTextField

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = authUiState != AuthUiState.Loading
            ) //passwordTextField

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.signIn(email, password)
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = authUiState != AuthUiState.Loading
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = stringResource(id = R.string.new_user_sign_up),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onClickSignUp()
                    },
            )

            when (authUiState) {
                AuthUiState.Loading -> LoadingIndicator()
                is AuthUiState.Error -> {
                    ShowToast(authUiState.errorMessage)
                }

                AuthUiState.Success ->
                    LaunchedEffect(Unit) {
                        onLoginSuccessful()
                    }

                else -> { /* Handle other states if needed */
                }
            }

        }
    }

}


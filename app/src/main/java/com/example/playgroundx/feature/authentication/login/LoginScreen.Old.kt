package com.example.playgroundx.feature.authentication.login


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playgroundx.R
import com.example.playgroundx.common.ext.basicButton
import com.example.playgroundx.common.ext.fieldModifier
import com.example.playgroundx.common.ext.spacer
import com.example.playgroundx.common.ext.textButton
import com.example.playgroundx.common.iconResourceId
import com.example.playgroundx.feature.components.BasicButton
import com.example.playgroundx.feature.components.BasicTextButton
import com.example.playgroundx.feature.components.BasicToolbar
import com.example.playgroundx.feature.components.EmailField
import com.example.playgroundx.feature.components.LoadingIndicator
import com.example.playgroundx.feature.components.PasswordField
import com.example.playgroundx.R.string as AppText


@Composable
fun LoginScreenOld(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isLoading = uiState.isLoading

    val updatedIconResourceId = rememberUpdatedState(iconResourceId)

    BasicToolbar(title = R.string.login_details)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val focusRequester = remember { FocusRequester() }

        Crossfade(targetState = updatedIconResourceId.value) {
            Image(
                painter = painterResource(id = it),
                contentDescription = "App Logo",
                modifier = Modifier.width(250.dp)
            )
        }

        Spacer(modifier = Modifier.spacer())
        Spacer(modifier = Modifier.spacer())


        EmailField(
            uiState.email, viewModel::onEmailChange, Modifier.fieldModifier(), !isLoading
        )

        Spacer(modifier = Modifier.spacer())

        PasswordField(
            uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier(), !isLoading
        )

        Spacer(modifier = Modifier.spacer())

        BasicButton(AppText.sign_in, Modifier.basicButton(), !isLoading, action = {
            viewModel.onClickSignIn(
                openAndPopUp
            )
        })

        LoadingIndicator(isLoading)

        BasicTextButton(AppText.forgot_password, Modifier.textButton(), !isLoading) {
            viewModel.onForgotPasswordClick()
        }

        BasicTextButton(AppText.new_user_sign_up, Modifier.textButton(), !isLoading) {
            viewModel.onSignUpClick(openAndPopUp)
        }
    }

}


package com.example.playgroundx.feature.authentication.signup

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.example.playgroundx.feature.components.RepeatPasswordField
import com.example.playgroundx.ui.theme.PlaygroundXJetComposeTheme
import com.example.playgroundx.R.string as AppText


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState

    val uiEvents by viewModel.uiEvents.collectAsStateWithLifecycle()

    val isLoading = uiEvents.isLoading

    SignUpScreenContent(uiState = uiState,
        isLoading = isLoading,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignInClick = { viewModel.onClickSignIn(openAndPopUp) },
        onSignUpClick = { viewModel.onClickSignUp(openAndPopUp) })

}


@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {

    val fieldModifier = Modifier.fieldModifier()

    val updatedIconResourceId = rememberUpdatedState(iconResourceId)

    BasicToolbar(AppText.create_account)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentHeight()
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Crossfade(targetState = updatedIconResourceId.value) {
            Image(
                painter = painterResource(id = it),
                contentDescription = "App Logo",
                modifier = Modifier.width(250.dp)
            )
        }


        Spacer(modifier = Modifier.spacer())
        Spacer(modifier = Modifier.spacer())


//        BasicField(AppText.username, uiState.username, viewModel::onEmailChange, fieldModifier)
        EmailField(uiState.email, onEmailChange, fieldModifier, !isLoading)
        Spacer(modifier = Modifier.spacer())

        PasswordField(uiState.password, onPasswordChange, fieldModifier, !isLoading)
        Spacer(modifier = Modifier.spacer())

        RepeatPasswordField(
            uiState.repeatPassword, onRepeatPasswordChange, fieldModifier, !isLoading
        )
        Spacer(modifier = Modifier.spacer())


        BasicButton(AppText.create_account, Modifier.basicButton(), !isLoading, onSignUpClick)

        LoadingIndicator(isLoading)

        BasicTextButton(
            AppText.already_have_an_account, Modifier.textButton(), !isLoading, onSignInClick
        )

    }

}


@Preview(name = "Sign up dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Sign up light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpPreview() {
    PlaygroundXJetComposeTheme() {
        SignUpScreenContent(
            uiState = SignUpUiState(),
            isLoading = false,
            onEmailChange = {},
            onPasswordChange = {},
            onRepeatPasswordChange = {},
            onSignInClick = {},
            onSignUpClick = {},
        )
    }
}

package com.example.playgroundx.presentation.feature.authentication.login


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playgroundx.R
import com.example.playgroundx.util.ext.basicButton
import com.example.playgroundx.util.ext.fieldModifier
import com.example.playgroundx.util.ext.spacer
import com.example.playgroundx.util.ext.textButton
import com.example.playgroundx.common.iconResourceId
import com.example.playgroundx.presentation.composables.BasicButton
import com.example.playgroundx.presentation.composables.BasicTextButton
import com.example.playgroundx.presentation.composables.BasicToolbar
import com.example.playgroundx.presentation.composables.EmailField
import com.example.playgroundx.presentation.composables.LoadingIndicator
import com.example.playgroundx.presentation.composables.PasswordField
import com.example.playgroundx.ui.theme.PlaygroundXJetComposeTheme
import com.example.playgroundx.R.string as AppText


@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LoginScreenContent(
        uiState = uiState,
        isLoading = uiState.isLoading,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onClickSignIn(openAndPopUp) },
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick,
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {

    val updatedIconResourceId = rememberUpdatedState(iconResourceId)

    BasicToolbar(title = R.string.login_details)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentHeight()
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
            uiState.email, onEmailChange, Modifier.fieldModifier(), !isLoading
        )

        Spacer(modifier = Modifier.spacer())

        PasswordField(
            uiState.password, onPasswordChange, Modifier.fieldModifier(), !isLoading
        )

        Spacer(modifier = Modifier.spacer())

        BasicButton(AppText.sign_in, Modifier.basicButton(), !isLoading, action = onSignInClick)

        LoadingIndicator(isLoading)

        BasicTextButton(
            AppText.forgot_password,
            Modifier.textButton(),
            !isLoading,
            action = onForgotPasswordClick
        )

        BasicTextButton(
            AppText.new_user_sign_up, Modifier.textButton(), !isLoading, action = onSignUpClick
        )
    }

}


@Preview(name = "Sign in dark theme", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Sign in light theme", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun SignInPreview() {
    PlaygroundXJetComposeTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            isLoading = false,
            onEmailChange = {},
            onPasswordChange = {},
            onSignInClick = {},
            onSignUpClick = {},
            onForgotPasswordClick = {},
        )
    }
}
package com.example.playgroundx.feature.authentication.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playgroundx.R
import com.example.playgroundx.common.ext.basicButton
import com.example.playgroundx.common.ext.fieldModifier
import com.example.playgroundx.common.ext.textButton
import com.example.playgroundx.feature.components.BasicButton
import com.example.playgroundx.feature.components.BasicTextButton
import com.example.playgroundx.feature.components.BasicToolbar
import com.example.playgroundx.feature.components.EmailField
import com.example.playgroundx.feature.components.PasswordField
import com.example.playgroundx.feature.components.RepeatPasswordField
import com.example.playgroundx.R.string as AppText


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreenOld(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

    val uiEvents by viewModel.uiEvents.collectAsStateWithLifecycle()

    val isLoading = uiEvents.isLoading

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

        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "App Logo",
            modifier = Modifier
                .width(250.dp)
                .padding(top = 16.dp)
                .padding(16.dp),
        )

//        BasicField(AppText.username, uiState.username, viewModel::onEmailChange, fieldModifier)
        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier, !isLoading)
        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier, !isLoading)
        RepeatPasswordField(
            uiState.repeatPassword, viewModel::onRepeatPasswordChange, fieldModifier, !isLoading
        )

        BasicButton(AppText.create_account, Modifier.basicButton(), !isLoading) {
            viewModel.onClickSignUp(openAndPopUp)
        }

        BasicTextButton(AppText.already_have_an_account, Modifier.textButton(), !isLoading) {
            viewModel.onClickSignIn(openAndPopUp)
        }

    }

}


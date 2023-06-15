package com.example.playgroundx.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playgroundx.R
import com.example.playgroundx.common.ext.basicButton
import com.example.playgroundx.common.ext.smallSpacer
import com.example.playgroundx.common.ext.spacer
import com.example.playgroundx.common.ext.textModifier
import com.example.playgroundx.feature.components.BasicButton
import com.example.playgroundx.feature.components.BasicToolbar
import com.example.playgroundx.util.getVersionInfo


@Composable
fun SettingsScreen(
    navigate: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val email by viewModel.email

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        BasicToolbar(R.string.settings)

        SettingTitle("Account Settings")

        SettingItem(icon = Icons.Default.AccountCircle,
            title = "Profile",
            onClick = { viewModel.onClickProfile(navigate) })

        SettingItem(icon = Icons.Default.Lock,
            title = "Security",
            onClick = { /* Handle security click */ })

        SettingItem(icon = Icons.Default.Notifications,
            title = "Notifications",
            onClick = { /* Handle notifications click */ })

        Spacer(modifier = Modifier.spacer())

        Divider()

        Spacer(modifier = Modifier.spacer())

        SettingTitle("App Settings")

        SettingItem(icon = painterResource(R.drawable.ic_language_filled),
            title = "Language",
            onClick = { /* Handle language click */ })

        SettingItem(icon = painterResource(R.drawable.ic_theme_outlined),
            title = "Theme",
            onClick = { /* Handle theme click */ })

        Spacer(modifier = Modifier.spacer())

        Text(
            text = getVersionInfo(),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.textModifier()
        )

        Spacer(modifier = Modifier.smallSpacer())

        // Push "Sign Out" button to the end
        Spacer(modifier = Modifier.weight(1f))

        BasicButton(text = R.string.sign_out, modifier = Modifier.basicButton()) {
            viewModel.onClickSignOut(openAndPopUp)
        }

        Spacer(modifier = Modifier.spacer())

    }

}

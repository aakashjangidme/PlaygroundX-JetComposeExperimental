package com.example.playgroundx.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playgroundx.R
import com.example.playgroundx.common.ext.basicButton
import com.example.playgroundx.common.ext.smallSpacer
import com.example.playgroundx.feature.components.BasicButton
import com.example.playgroundx.feature.components.BasicToolbar
import com.example.playgroundx.feature.components.LoadingIndicator
import com.example.playgroundx.feature.profile.composables.ProfileImage


@Composable
//@Preview
fun ProfileScreen(
    navigate: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState

    val isLoading = uiState.isLoading

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BasicToolbar(R.string.profile)

        LoadingIndicator(isLoading)

        // User Image
        ProfileImage(imageUrl = uiState.imageUrl, viewModel.initials, onClick = {})

        Spacer(modifier = Modifier.height(16.dp))

        // User Name and Username

        ProfileHeaderText(
            text = uiState.name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        ProfileHeaderText(
            text = "@${uiState.userName}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // User Details
        ProfileDetail(icon = Icons.Default.Person, text = "Full Name", value = uiState.name)
        ProfileDetail(icon = Icons.Default.Email, text = "Email", value = uiState.email)
        ProfileDetail(
            icon = Icons.Default.Phone, text = "Phone Number", value = uiState.phoneNumber
        )

        Spacer(modifier = Modifier.smallSpacer())

        // Push content to the end
        Spacer(modifier = Modifier.weight(1f))

        // Edit Button
        BasicButton(text = R.string.edit,
            Modifier.basicButton(),
            action = { viewModel.onClickEditProfile(navigate) })

        Spacer(modifier = Modifier.smallSpacer())
    }

}


@Composable
fun ProfileHeaderText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        style = style,
        textAlign = textAlign,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}

@Composable
fun ProfileDetail(icon: ImageVector, text: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            ProfileHeaderText(text = text, style = MaterialTheme.typography.titleMedium)
            ProfileHeaderText(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(Color.Gray)
            )
        }
    }
}


@Composable
fun EditButton(onClick: () -> Unit) {
    BasicButton(text = R.string.edit, Modifier.basicButton()) { onClick() }
}
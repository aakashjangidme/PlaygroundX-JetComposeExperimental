package com.example.playgroundx.presentation.feature.profile.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.playgroundx.R
import com.example.playgroundx.util.ext.basicButton
import com.example.playgroundx.util.ext.fieldModifier
import com.example.playgroundx.util.ext.smallSpacer
import com.example.playgroundx.presentation.composables.BasicButton
import com.example.playgroundx.presentation.composables.BasicField
import com.example.playgroundx.presentation.composables.BasicToolbar
import com.example.playgroundx.presentation.composables.LoadingIndicator
import com.example.playgroundx.presentation.feature.profile.ProfileViewModel
import com.example.playgroundx.presentation.feature.profile.composables.ProfileImage


@Composable
fun ProfileEditScreen(
    navigate: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState

    val isLoading = uiState.isLoading


    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }


    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // The user has selected an image, handle it here
                imageUri.value = uri
                viewModel.uploadProfilePicture(uri)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        BasicToolbar(R.string.edit_profile)

        LoadingIndicator(isLoading)

        // User Image
        ProfileImage(imageUrl = uiState.imageUrl, viewModel.initials, onClick = {
            imagePickerLauncher.launch("image/*")
        })

        /*        // Display the selected image if available
                if (imageUri.value != null) {
                    AndroidView(factory = { context ->
                        // Show the selected image using an AndroidView
                        // You can use any image view or image-related composable of your choice
                        ImageView(context).apply {
                            setImageURI(imageUri.value)
                        }
                    })
                }*/

        Spacer(modifier = Modifier.height(16.dp))

        val fieldModifier = Modifier.fieldModifier()

        // Editable Fields
        BasicField(
            "Email", uiState.email, {}, fieldModifier, false
        )
        BasicField(
            "Full Name", uiState.name, viewModel::onFullNameChange, fieldModifier, !isLoading
        )
        BasicField(
            "Username",
            uiState.userName,
            viewModel::onUserNameChange,
            fieldModifier,
            !isLoading,
        )
        BasicField(
            "Phone", uiState.phoneNumber, viewModel::onPhoneChange, fieldModifier, !isLoading
        )
        BasicField(
            "Bio", uiState.bio, viewModel::onBioChange, fieldModifier, !isLoading
        )

        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicButton(text = R.string.save, Modifier.basicButton(), !isLoading, action = {
                viewModel.onClickSave(openAndPopUp)
            })

            BasicButton(text = R.string.dismiss, Modifier.basicButton(), action = { })
        }


        Spacer(modifier = Modifier.smallSpacer())
    }

}

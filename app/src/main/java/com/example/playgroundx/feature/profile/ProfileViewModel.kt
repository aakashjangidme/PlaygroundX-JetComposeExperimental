package com.example.playgroundx.feature.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.example.playgroundx.R
import com.example.playgroundx.common.Resource
import com.example.playgroundx.common.Screens
import com.example.playgroundx.common.snackbar.SnackbarManager
import com.example.playgroundx.common.snackbar.SnackbarMessage
import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.domain.service.LogService
import com.example.playgroundx.domain.service.StorageService
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import com.example.playgroundx.domain.usecase.userUseCases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    logService: LogService,
    private val userUseCases: UserUseCases,
    private val authUseCase: AuthUseCase,
    private val storageService: StorageService, //TODO: create editProfileViewModel and do this stuff and also create a Use case for it, since we are not directly using anything from repo in our view models
) : BaseViewModel(logService) {

    private val userId = authUseCase.currentAuthUser()?.uid!!


    ///TODO: which state flow should be used?
    var uiState = mutableStateOf(ProfileUiState())
        private set

    private val fullName
        get() = uiState.value.name
    private val userName
        get() = uiState.value.userName
    private val phoneNumber
        get() = uiState.value.phoneNumber
    private val bio
        get() = uiState.value.bio

    // Extract initials from email or username
    val initials: String
        get() = getInitials(uiState.value.email, uiState.value.name)


    fun onFullNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onUserNameChange(newValue: String) {
        uiState.value = uiState.value.copy(userName = newValue)
    }

    fun onPhoneChange(newValue: String) {
        uiState.value = uiState.value.copy(phoneNumber = newValue)
    }

    fun onBioChange(newValue: String) {
        uiState.value = uiState.value.copy(bio = newValue)
    }

    init {
        getUserInfo(userId)
    }


    private fun getUserInfo(userId: String) {
        launchCatching {
            userUseCases.getUserDetails(userid = userId).collect {
                uiState.value = when (it) {
                    is Resource.Success -> {
                        uiState.value.copy(
                            isLoading = false,
                            name = it.data!!.name,
                            userName = it.data.userName,
                            email = it.data.email,
                            password = it.data.password,
                            imageUrl = it.data.imageUrl,
                            phoneNumber = it.data.phoneNumber,
                            bio = it.data.bio,
                        )
                    }

                    is Resource.Error -> {
                        uiState.value.copy(isLoading = false)
                    }

                    is Resource.Loading -> {
                        uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }


    fun onClickEditProfile(navigate: (String) -> Unit) {
        navigate(Screens.ProfileEditScreen.route)
    }


    private fun setUserInfo(
        name: String,
        username: String,
        bio: String,
        phoneNumber: String,
        onSuccess: () -> Unit,
    ) {
        launchCatching {
            userUseCases.setUserDetails(
                userid = userId,
                name = name,
                userName = username,
                bio = bio,
                websiteUrl = "",
                phoneNumber = phoneNumber
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        uiState.value = uiState.value.copy(isLoading = false);
                        onSuccess()
                    }

                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(isLoading = false)
                    }

                    is Resource.Loading -> {
                        uiState.value = uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }


    fun onClickSave(openAndPopUp: (String, String) -> Unit) {

        //validations
        if (fullName.isBlank()) {
            SnackbarManager.showMessage(R.string.error_name_empty)
            return
        }

        if (userName.isBlank()) {
            SnackbarManager.showMessage(R.string.error_username_empty)
            return
        }

        if (phoneNumber.isBlank()) {
            SnackbarManager.showMessage(R.string.error_phoneNumber_empty)
            return
        }

        setUserInfo(
            name = fullName, username = userName, bio = bio, phoneNumber = phoneNumber
        ) {
            openAndPopUp(Screens.ProfileScreen.route, Screens.ProfileEditScreen.route)
        }

    }


    fun uploadProfilePicture(imageUri: Uri) {

        launchCatching {

            val downloadUrl = withContext(Dispatchers.IO) {
                storageService.uploadProfilePicture(userId, imageUri)
            }

            if (downloadUrl != null) {

                userUseCases.setUserProfilePicture(userId, downloadUrl).collect() {

                    uiState.value = when (it) {
                        is Resource.Success -> {
                            uiState.value.copy(isLoading = false, imageUrl = downloadUrl)
                        }

                        is Resource.Error -> {
                            uiState.value.copy(isLoading = false)
                        }

                        is Resource.Loading -> {
                            uiState.value.copy(isLoading = true)
                        }
                    }
                }
            } else {
                SnackbarManager.showMessage(SnackbarMessage.StringSnackbar("Image couldn't be uploaded"))
            }
        }
    }
}

private fun getInitials(email: String, fullName: String): String {
    return when {
        fullName.isNotBlank() -> {
            val names = fullName.trim().split(" ")
            val initials = StringBuilder()
            for (name in names) {
                if (name.isNotBlank()) {
                    initials.append(name[0])
                }
            }
            initials.toString().uppercase(Locale.getDefault())
        }

        email.isNotBlank() -> email.substring(0, 1).uppercase(Locale.getDefault())
        else -> ""
    }
}


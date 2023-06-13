package com.example.playgroundx.feature.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.domain.model.AppUser
import com.example.playgroundx.domain.usecase.userUseCases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userUseCases: UserUseCases,
) : ViewModel() {

    private val userId = auth.currentUser?.uid!!

    private val _getUserData = mutableStateOf<Resource<AppUser?>>(Resource.Success(null))
    val getUserData: State<Resource<AppUser?>> = _getUserData

    private val _setUserData = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val setUserData: State<Resource<Boolean>> = _setUserData

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            userUseCases.getUserDetails(userid = userId).collect {
                _getUserData.value = it as Resource<AppUser?>
            }
        }
    }

    fun setUserInfo(name: String, username: String, bio: String, websiteUrl: String) {
        viewModelScope.launch {
            userUseCases.setUserDetails(
                userid = userId,
                name = name,
                userName = username,
                bio = bio,
                websiteUrl = websiteUrl
            ).collect {
                _setUserData.value = it
            }
        }
    }
}
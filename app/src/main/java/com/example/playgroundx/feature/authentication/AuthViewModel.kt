package com.example.playgroundx.feature.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCase,
) : ViewModel() {

    private val _isUserAuthenticated = mutableStateOf<Resource<Boolean>>(Resource.Loading())
    val isUserAuthenticated: State<Resource<Boolean>> = _isUserAuthenticated

    private val _signInState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signInState: State<Resource<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signUpState: State<Resource<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    val signOutState: State<Resource<Boolean>> = _signOutState

    private val _firebaseAuthState = mutableStateOf<Boolean>(false)
    val firebaseAuthState: State<Boolean> = _firebaseAuthState

    init {
        fetchUserAuthenticationStatus()
    }

    private fun fetchUserAuthenticationStatus() {
        viewModelScope.launch {
            try {
                val isAuthenticated = authUseCases.isUserAuthenticated()
                _isUserAuthenticated.value = Resource.Success(isAuthenticated)
            } catch (e: Exception) {
                _isUserAuthenticated.value = Resource.Error(e.localizedMessage!!.toString())
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            authUseCases.firebaseSignOut().collect {
                _signOutState.value = it
                if (it == Resource.Success(true)) {
                    _signInState.value = Resource.Success(false)
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignIn(email = email, password = password).collect {
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignUp(email = email, password = password, userName = username)
                .collect {
                    _signUpState.value = it
                }
        }
    }

    fun getFirebaseAuthState() {
        viewModelScope.launch {
            authUseCases.firebaseAuthState().collect {
                _firebaseAuthState.value = it
            }
        }
    }
}
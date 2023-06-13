package com.example.playgroundx.feature.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playgroundx.core.Argument
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.domain.model.Details
import com.example.playgroundx.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: GetUserDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val username: String = checkNotNull(savedStateHandle.get<String>(Argument.USERNAME))

    private val _state = mutableStateOf(DetailsUiState())

    //state will be accessed by UI
    var state: State<DetailsUiState> = _state

    init {
        getUserDetail(username)
    }

    private fun getUserDetail(username: String) {
        viewModelScope.launch(Dispatchers.IO) {

            Timber.tag("DetailsViewModel::Dispatchers.IO")
                .d("Current thread: ${Thread.currentThread().name}")

            useCase(username).collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Resource.Success -> {
                            _state.value = DetailsUiState(detail = result.data ?: Details())
                        }

                        is Resource.Error -> {
                            _state.value = DetailsUiState(
                                error = result.message ?: "An unexpected error occurred."
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = DetailsUiState(isLoading = true)
                        }
                    }
                }
            }
        }
    }

}
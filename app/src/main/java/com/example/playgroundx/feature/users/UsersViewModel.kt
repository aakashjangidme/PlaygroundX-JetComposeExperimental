package com.example.playgroundx.feature.users


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playgroundx.core.common.Resource
import com.example.playgroundx.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
}

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val useCase: GetUsersUseCase
) : ViewModel() {

    //state will be accessed by UI
    private val _state = mutableStateOf(UsersUiState())
    var state: State<UsersUiState> = _state


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getUsersJob: Job? = null;

    init {
        fetchUsersFromCache()
    }

    private fun fetchUsersFromCache() {
        getUsersJob?.cancel();

        getUsersJob = viewModelScope.launch(Dispatchers.IO) {

            Timber.tag("UsersViewModel::Dispatchers.IO")
                .d("Current thread: ${Thread.currentThread().name}")

            useCase().collect { result ->
                withContext(Dispatchers.Main) {

                    when (result) {
                        is Resource.Success -> {
                            _state.value = UsersUiState(
                                list = result.data ?: emptyList()
                            )
                        }

                        is Resource.Error -> {
                            _state.value = UsersUiState(
                                list = result.data ?: emptyList()
                            )

                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = UsersUiState(
                                list = result.data ?: emptyList(), isLoading = true
                            )
                        }
                    }
                }

                Timber.tag("UsersViewModel").d("Finished with State")
            }

        }
    }
}

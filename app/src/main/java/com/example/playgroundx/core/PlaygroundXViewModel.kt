package com.example.playgroundx.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


open class PlaygroundXViewModel : ViewModel() {

    var isScreenLoading = MutableLiveData(true)
        private set

    init {
        viewModelScope.launch {
            fetchRequiredResources()
            isScreenLoading.value = false
        }
    }

    private suspend fun fetchRequiredResources() {
        val fakeExecutionTime = 300L
        delay(fakeExecutionTime)
    }

}

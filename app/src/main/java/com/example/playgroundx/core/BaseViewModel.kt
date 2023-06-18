package com.example.playgroundx.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playgroundx.util.snackbar.SnackbarManager
import com.example.playgroundx.util.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.playgroundx.domain.service.LogService

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel(private val logService: LogService) : ViewModel() {

    /**
     * Launch the coroutine, and catch/log any exception to the LogService.
     *
     * This function shows snackbar by default if any exception occurred
     * snackbar message will come from exception.message or a generic string if exception.message is empty
     *
     * Use flowOn, or withContext to launch it flows/coroutines on desired threads.
     *
     * Handle and throw errors in the repository/service layer, which later shall be catchable by this function
     */
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            }, block = block
        )
}

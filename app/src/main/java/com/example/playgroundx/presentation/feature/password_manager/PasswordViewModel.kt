package com.example.playgroundx.presentation.feature.password_manager

import com.example.playgroundx.core.BaseViewModel
import com.example.playgroundx.data.local.entity.PasswordEntity
import com.example.playgroundx.domain.repository.PasswordRepository
import com.example.playgroundx.domain.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class PasswordViewModel @Inject constructor(
    logService: LogService,
    private val repository: PasswordRepository,
) : BaseViewModel(logService) {


    private val _passwordEntriesState = MutableStateFlow<List<PasswordEntity>>(emptyList())
    val passwordEntriesState: StateFlow<List<PasswordEntity>> = _passwordEntriesState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    init {
        fetchPasswordEntries()
    }


    private fun fetchPasswordEntries() {
        launchCatching {
            try {
                _loadingState.value = true

                repository.getPasswordEntries().collect { passwordEntries ->

                    _passwordEntriesState.update { passwordEntries }

                    _loadingState.value = false
                    _errorState.value = null
                }

            } catch (e: Exception) {
                _errorState.value = "Failed to fetch password entries: ${e.message}"
                throw e
            } finally {
                _loadingState.value = false
            }
        }
    }


    fun onAddPasswordEntry(passwordEntity: PasswordEntity) {
        launchCatching {
            try {
                _loadingState.value = true
                repository.addPasswordEntry(passwordEntity)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Failed to add password entries: ${e.message}"
                throw e
            } finally {
                _loadingState.value = false
            }
        }
    }

    suspend fun updatePasswordEntry(passwordEntity: PasswordEntity) {
        repository.updatePasswordEntry(passwordEntity)
    }


    suspend fun getPasswordEntryById(entryId: String): PasswordEntity? {
        return repository.getPasswordEntryById(entryId)
    }

    fun onPasswordEntrySelected(passwordEntity: PasswordEntity) {

    }

    fun onDeletePasswordEntry(passwordEntity: PasswordEntity) {

        launchCatching {
            repository.deletePasswordEntry(passwordEntity)
        }

    }
}

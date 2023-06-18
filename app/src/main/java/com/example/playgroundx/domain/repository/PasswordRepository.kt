package com.example.playgroundx.domain.repository

import com.example.playgroundx.data.local.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    suspend fun getPasswordEntries(): Flow<List<PasswordEntity>>

    suspend fun addPasswordEntry(passwordEntity: PasswordEntity)

    suspend fun updatePasswordEntry(passwordEntity: PasswordEntity)

    suspend fun deletePasswordEntry(passwordEntity: PasswordEntity)

    suspend fun getPasswordEntryById(entryId: String): PasswordEntity?
}

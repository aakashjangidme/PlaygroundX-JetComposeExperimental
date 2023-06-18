package com.example.playgroundx.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.playgroundx.data.local.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordEntryDao {
    @Query("SELECT * FROM password_entries")
    fun getPasswordEntries(): Flow<List<PasswordEntity>>

    @Insert
    suspend fun addPasswordEntry(passwordEntity: PasswordEntity)

    @Update
    suspend fun updatePasswordEntry(passwordEntity: PasswordEntity)

    @Delete
    suspend fun deletePasswordEntry(passwordEntity: PasswordEntity)

    @Query("SELECT * FROM password_entries WHERE id = :entryId LIMIT 1")
    suspend fun getPasswordEntryById(entryId: String): PasswordEntity?
}

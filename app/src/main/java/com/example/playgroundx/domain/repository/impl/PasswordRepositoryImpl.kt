package com.example.playgroundx.domain.repository.impl

import com.example.playgroundx.data.local.dao.PasswordEntryDao
import com.example.playgroundx.data.local.entity.PasswordEntity
import com.example.playgroundx.domain.repository.PasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(private val dao: PasswordEntryDao) :
    PasswordRepository {
    override suspend fun getPasswordEntries(): Flow<List<PasswordEntity>> {
        return withContext(Dispatchers.IO) {

            println("Current Thread " + Thread.currentThread().name)

            dao.getPasswordEntries()
        }
    }

    override suspend fun addPasswordEntry(passwordEntity: PasswordEntity) {

        return withContext(Dispatchers.IO) {
            dao.addPasswordEntry(passwordEntity)
        }
    }

    override suspend fun updatePasswordEntry(passwordEntity: PasswordEntity) {
        return withContext(Dispatchers.IO) {
            dao.updatePasswordEntry(passwordEntity)
        }
    }

    override suspend fun deletePasswordEntry(passwordEntity: PasswordEntity) {

        return withContext(Dispatchers.IO) {
            dao.deletePasswordEntry(passwordEntity)
        }
    }

    override suspend fun getPasswordEntryById(entryId: String): PasswordEntity? {

        return withContext(Dispatchers.IO) {
            dao.getPasswordEntryById(entryId)
        }
    }
}

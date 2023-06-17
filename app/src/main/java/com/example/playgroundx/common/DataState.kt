package com.example.playgroundx.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}

fun <T> Flow<T>.asDataState(): Flow<DataState<T>> {
    return this.map<T, DataState<T>> { DataState.Success(it) }.onStart { emit(DataState.Loading) }
        .catch { emit(DataState.Error(it.localizedMessage ?: "Something went wrong.")) }
}
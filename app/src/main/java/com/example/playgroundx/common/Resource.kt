package com.example.playgroundx.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Success<out T>(data: T) : Resource<T>(data)
    class Error<out T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<out T>(data: T? = null) : Resource<T>(data)
}

fun <T> Resource<T>.successOr(fallback: T): T {
    return (this as? Resource.Success<T>)?.data ?: fallback
}


fun <T> Flow<T>.asResponse(): Flow<Resource<T>> {
    return this.map<T, Resource<T>> {
        Resource.Success(it)
    }.onStart { emit(Resource.Loading()) }
        .catch { emit(Resource.Error(it.localizedMessage?.toString() ?: "Something went wrong.")) }
}


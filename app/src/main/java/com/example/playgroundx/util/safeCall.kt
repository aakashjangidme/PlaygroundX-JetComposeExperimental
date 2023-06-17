package com.example.playgroundx.util

import com.example.playgroundx.common.Resource
import timber.log.Timber

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Timber.tag("safeCall").d(e.message.toString())
        Resource.Error(e.message ?: "An unknown Error Occurred")
    }
}
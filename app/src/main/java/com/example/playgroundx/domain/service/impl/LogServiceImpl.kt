package com.example.playgroundx.domain.service.impl

import android.util.Log
import com.example.playgroundx.domain.service.LogService

import javax.inject.Inject

class LogServiceImpl @Inject constructor() : LogService {

    override fun logNonFatalCrash(throwable: Throwable) {
        //        Firebase.crashlytics.recordException(throwable)
        Log.d("LogServiceImpl", throwable.message, throwable)
    }
}

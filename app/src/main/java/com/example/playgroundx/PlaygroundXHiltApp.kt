package com.example.playgroundx


import android.app.Application
import com.example.playgroundx.util.getVersionInfo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class PlaygroundXHiltApp : Application() {

    private var isDebuggable = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        if (isDebuggable) Timber.plant(DebugTree())

        Timber.tag(TAG).d("VersionInfo : ${getVersionInfo()}")

    }


    companion object {
        private val TAG: String = this::class.java.simpleName
    }
}
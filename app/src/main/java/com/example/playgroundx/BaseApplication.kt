package com.example.playgroundx


import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.text.SimpleDateFormat
import java.util.Locale


@HiltAndroidApp
class BaseApplication : Application() {

    private var isDebuggable = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        if (isDebuggable) Timber.plant(DebugTree())

        Timber.tag(TAG).d("VersionInfo : $getVersionInfo")

    }

    private val getVersionInfo: String = String.format(
        format = "%s %s %s@%s",
        "V${BuildConfig.VERSION_NAME}",
        BuildConfig.VERSION_CODE,
        BuildConfig.BUILD_TYPE,
        SimpleDateFormat("dd MMM yyy", Locale.US).format(BuildConfig.BUILD_TIME),
    )

    companion object {
        private val TAG: String = this::class.java.simpleName
    }
}
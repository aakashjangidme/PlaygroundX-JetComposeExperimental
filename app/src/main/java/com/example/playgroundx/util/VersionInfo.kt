package com.example.playgroundx.util

import com.example.playgroundx.BuildConfig
import java.text.SimpleDateFormat
import java.util.Locale


fun getVersionInfo(): String = String.format(
    format = "%s %s %s@%s",
    "V${BuildConfig.VERSION_NAME}",
    BuildConfig.VERSION_CODE,
    BuildConfig.BUILD_TYPE,
    SimpleDateFormat("dd MMM yyy", Locale.US).format(BuildConfig.BUILD_TIME),
)
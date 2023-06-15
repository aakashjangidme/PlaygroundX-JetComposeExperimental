package com.example.playgroundx.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateUTC: String?): String {
    if (dateUTC.isNullOrEmpty()) return ""

    val date = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(dateUTC)
    val newFormat = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
    return date?.let { newFormat.format(it) }.orEmpty()
}
package com.rago.hnmobiletest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

fun getTimeArticles(date: String): String {
    val text = OffsetDateTime.parse(date).format(
        DateTimeFormatter.ofPattern(
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            Locale.ENGLISH
        )
    )
    val now: LocalDateTime = LocalDateTime.now()
    val articlesDate: LocalDateTime = LocalDateTime.parse(text)
    val diff = ChronoUnit.SECONDS.between(now, articlesDate)
    val minutes = diff / 60
    return if (minutes > 60) {
        val hours = minutes / 60
        if (hours > 24) {
            "yesterdays"
        } else
            "$hours h"
    } else
        "$minutes m"
}

fun isInternetAvailable(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}
package com.rago.hnmobiletest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

//Obtenemos la resta de la fecha del articulo contra la fecha del dispositivo.
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

//Validamos la conexion del dispositivo para el uso del cache en retrofit
fun isInternetAvailable(context: Context): Boolean {
    val connectionManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        )
                as ConnectivityManager
    val networkCapabilities = connectionManager.activeNetwork ?: return false
    val actNw =
        connectionManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
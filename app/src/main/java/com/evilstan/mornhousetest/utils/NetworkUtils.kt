package com.evilstan.mornhousetest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isNetworkAvailable(context: Context): Boolean {

    val mConnectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    val network = mConnectivityManager.activeNetwork ?: return false
    val networkCapabilities = mConnectivityManager.getNetworkCapabilities(network) ?: return false
    return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}
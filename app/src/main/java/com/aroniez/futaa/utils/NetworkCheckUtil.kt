package com.aroniez.futaa.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkCheckUtil {
    fun connectedToTheNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }
}
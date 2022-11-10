package com.example.mymovies.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Utility {

    fun checkInternetConnection(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // if the android version is above M
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            val activeNetwork =
                connectivity.getNetworkCapabilities(connectivity.activeNetwork) ?: return false
            return when {
                // Indicates this network uses a Wi-Fi transport, or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                // Indicates this network uses a Cellular transport, or Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // Indicates this network have network Capability
                activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
                // else return false
                else -> false
            }

        } else {
            // if the android version is below M
            @Suppress("DEPRECATION")
            return connectivity.activeNetworkInfo?.isConnected ?: false
        }

    }
}
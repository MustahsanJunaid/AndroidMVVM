package com.mjb.android.mvvm.ktx

import android.app.Activity
import android.content.Context
import android.widget.Toast
import android.content.ContextWrapper
import android.content.Intent
import android.net.ConnectivityManager
import androidx.core.net.toUri


fun Context.startActivity(mClass: Class<*>) {
    startActivity(Intent(this, mClass))
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}
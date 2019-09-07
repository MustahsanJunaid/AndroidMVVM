package com.mjb.android.mvvm.network

import java.io.IOException

class ConnectivityException: IOException() {
    override val message: String?
        get() = "No Internet Connection"
}
package com.mjb.android.mvvm.network

import android.content.Context
import com.mjb.android.mvvm.ktx.isOnline
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isOnline()) {
            throw ConnectivityException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
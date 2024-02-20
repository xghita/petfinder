package com.ghitai.petfinder.data.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val encryptedSharedPreferences: SharedPreferences) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val modifiedRequest =
            if (!chain.request().url.toString().contains("token")) {
                originalRequest.newBuilder()
                    .header(
                        AUTHORIZATION_HEADER,
                        "$BEARER ${encryptedSharedPreferences.getString(KEY, null)}"
                    )
                    .build()
            } else {
                originalRequest
            }

        return chain.proceed(modifiedRequest)
    }
}
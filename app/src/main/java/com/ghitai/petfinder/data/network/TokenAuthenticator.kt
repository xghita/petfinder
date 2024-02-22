package com.ghitai.petfinder.data.network

import android.content.SharedPreferences
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

const val CLIENT_ID = "8FvB92COL3loJkRHBozGPLOVKZTG4CgXal6Dou6EjsH5lj2SXB"
const val CLIENT_SECRET = "zcYSA3CrhG6yW1dc539o8rAVgj7ecwLUaYHTSe3s"
const val CLIENT_CREDENTIALS = "client_credentials"

class TokenAuthenticator(
    private val encryptedSharedPreferences: SharedPreferences,
    private val petApi: () -> PetApi
) : Authenticator {


    private val lazyPetApi: PetApi by lazy { petApi() }

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request {

        val token = lazyPetApi.getAccessToken(
            grantType = CLIENT_CREDENTIALS,
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
        ).blockingGet().accessToken

        saveTokenToSharedPrefs(token)

        return response.request.newBuilder()
            .header(
                AUTHORIZATION_HEADER,
                "$BEARER ${encryptedSharedPreferences.getString(KEY, null)}"
            )
            .build()
    }

    private fun saveTokenToSharedPrefs(token: String) {
        val editor = encryptedSharedPreferences.edit()
        editor.putString(KEY, token)
        editor.apply()
    }


}
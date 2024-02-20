package com.ghitai.petfinder.data.network.model

import com.google.gson.annotations.SerializedName


data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int
)
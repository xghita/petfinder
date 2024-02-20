package com.ghitai.petfinder.data.network.model

import com.google.gson.annotations.SerializedName

data class Animal(
    @SerializedName("id") val id: Int,
    @SerializedName("breeds") val breeds: Breeds,
    @SerializedName("name") val name: String,
    @SerializedName("primary_photo_cropped") val photo: PrimaryPhotoCropped?,
)

data class Breeds(
    @SerializedName("primary") val primary: String,
)


data class PrimaryPhotoCropped(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String,
    @SerializedName("full") val full: String
)
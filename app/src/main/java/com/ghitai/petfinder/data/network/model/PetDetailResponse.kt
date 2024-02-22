package com.ghitai.petfinder.data.network.model

import com.google.gson.annotations.SerializedName


data class PetDetailResponse(@SerializedName("animal") val animal: AnimalDetail)

data class AnimalDetail(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("breeds") val breeds: Breeds,
    @SerializedName("gender") val gender: String,
    @SerializedName("size") val size: String,
    @SerializedName("name") val name: String,
    @SerializedName("primary_photo_cropped") val photo: PrimaryPhotoCropped?,
    @SerializedName("status") val status: String,
    @SerializedName("distance") val distance: Double
)


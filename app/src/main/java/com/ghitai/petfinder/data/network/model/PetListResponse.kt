package com.ghitai.petfinder.data.network.model

import com.google.gson.annotations.SerializedName

data class PetListResponse(
    @SerializedName("animals")
    val animals: List<Animal>
)
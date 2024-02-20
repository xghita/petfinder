package com.ghitai.petfinder.ui.detail

data class PetDetailViewState(
    val name: String = "",
    val breed: String = "",
    val size: String = "",
    val gender: String = "",
    val status: String = "",
    val distance: Double? = null,
    val photoUrl: String? = "",
)
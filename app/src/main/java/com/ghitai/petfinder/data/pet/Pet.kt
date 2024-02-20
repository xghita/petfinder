package com.ghitai.petfinder.data.pet

sealed class Pet {

    data class ListItem(
        val id: Int = -1,
        val type: String = "",
        val name: String = "",
        val photoUrl: String? = "",
    )

    data class Detail(
        val name: String = "",
        val breed: String = "",
        val size: String = "",
        val gender: String = "",
        val status: String = "",
        val distance: Double? = null,
        val photoUrl: String? = "",
    )
}
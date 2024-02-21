package com.ghitai.petfinder.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val breed: String,
    val size: String,
    val gender: String,
    val status: String,
    val distance: Double?,
    val photoUrl: String?
)
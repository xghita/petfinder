package com.ghitai.petfinder.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

const val PET_DATABASE = "PetDatabase.db"

@Database(entities = [PetDetailEntity::class], version = 1)
abstract class PetDetailDatabase : RoomDatabase() {
    abstract fun petDao(): PetDetailDao
}
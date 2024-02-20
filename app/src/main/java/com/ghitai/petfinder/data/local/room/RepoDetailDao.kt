package com.ghitai.petfinder.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PetDetailDao {
    @Query("SELECT * FROM PetDetailEntity WHERE id = :petId ")
    fun getPetDetail(petId: Int): Single<PetDetailEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPet(petDetail: PetDetailEntity): Completable
}

package com.ghitai.petfinder.data.local

import com.ghitai.petfinder.data.local.room.PetDetailDao
import com.ghitai.petfinder.data.local.room.PetDetailEntity
import com.ghitai.petfinder.data.pet.Pet
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalDataStorage {
    fun getPetDetail(petId: Int): Single<Pet.Detail>
    fun insertPet(petDetail: PetDetailEntity): Completable
}

class LocalDataStorageImpl(private val petDetailDao: PetDetailDao) : LocalDataStorage {

    override fun getPetDetail(petId: Int): Single<Pet.Detail> {
        return petDetailDao.getPetDetail(petId).map {
            Pet.Detail(
                name = it.name,
                breed = it.breed,
                size = it.size,
                gender = it.gender,
                status = it.status,
                distance = it.distance,
                photoUrl = it.photoUrl,
                )
        }.onErrorReturn { Pet.Detail() }
    }

    override fun insertPet(petDetail: PetDetailEntity): Completable {
        return petDetailDao.insertPet(petDetail)
    }

}
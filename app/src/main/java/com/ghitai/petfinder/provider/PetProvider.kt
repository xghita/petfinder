package com.ghitai.petfinder.provider

import com.ghitai.petfinder.data.local.LocalDataStorage
import com.ghitai.petfinder.data.local.room.PetDetailEntity
import com.ghitai.petfinder.data.network.PetApi
import com.ghitai.petfinder.data.network.model.PetListResponse
import com.ghitai.petfinder.data.pet.Pet
import com.ghitai.petfinder.extensions.PhotoExt
import com.ghitai.petfinder.extensions.getUrl
import io.reactivex.rxjava3.core.Single

interface PetProvider {
    fun getPetList(): Single<PetListResponse>
    fun getPetDetail(petId: Int): Single<Pet.Detail>
}

class PetProviderImpl(
    private val petApi: PetApi,
    private val localDataStorage: LocalDataStorage
) : PetProvider {

    override fun getPetList(): Single<PetListResponse> {
        return petApi.getPetList()
    }

    override fun getPetDetail(petId: Int): Single<Pet.Detail> {
        return localDataStorage.getPetDetail(petId)
            .flatMap { petDetail ->
                if (petDetail != Pet.Detail()) {
                    Single.just(petDetail)
                } else {
                    petApi.getPetDetail(petId)
                        .flatMap { petDetailResponse ->
                            localDataStorage.insertPet(
                                PetDetailEntity(
                                    id = petId,
                                    name = petDetailResponse.animal.name,
                                    breed = petDetailResponse.animal.breeds.primary,
                                    size = petDetailResponse.animal.size,
                                    gender = petDetailResponse.animal.gender,
                                    status = petDetailResponse.animal.status,
                                    distance = petDetailResponse.animal.distance,
                                    photoUrl = petDetailResponse.animal.photo?.getUrl(PhotoExt.PHOTO_FULL)
                                )
                            ).andThen(localDataStorage.getPetDetail(petId))
                        }
                }
            }
    }

}
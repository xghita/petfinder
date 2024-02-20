package com.ghitai.petfinder.ui.detail

import com.ghitai.petfinder.data.pet.Pet
import com.ghitai.petfinder.core.base.ViewStateMapper

class PetDetailViewStateMapper : ViewStateMapper<Pet.Detail, PetDetailViewState> {

    override fun toViewState(payload: Pet.Detail): PetDetailViewState {
        return PetDetailViewState(
            name = payload.name,
            breed = payload.breed,
            size = payload.size,
            gender = payload.gender,
            status = payload.status,
            distance = payload.distance,
            photoUrl = payload.photoUrl
        )
    }
}
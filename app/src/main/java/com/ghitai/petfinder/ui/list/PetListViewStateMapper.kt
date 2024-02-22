package com.ghitai.petfinder.ui.list

import com.ghitai.petfinder.core.base.ViewStateMapper
import com.ghitai.petfinder.data.network.model.PetListResponse
import com.ghitai.petfinder.data.pet.Pet
import com.ghitai.petfinder.extensions.PhotoExt
import com.ghitai.petfinder.extensions.getUrl

class PetListViewStateMapper : ViewStateMapper<PetListResponse, PetListViewState> {

    override fun toViewState(payload: PetListResponse): PetListViewState {

        return PetListViewState(
            animals = payload.animals.map {
                Pet.ListItem(
                    id = it.id,
                    name = it.name,
                    type = it.breeds.primary,
                    photoUrl = it.photo?.getUrl(PhotoExt.PHOTO_CROP)
                )
            }
        )
    }

}
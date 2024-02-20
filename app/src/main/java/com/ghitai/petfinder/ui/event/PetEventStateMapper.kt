package com.ghitai.petfinder.ui.event

import com.ghitai.petfinder.core.base.EventViewStateMapper
import com.ghitai.petfinder.core.events.ErrorUI
import com.ghitai.petfinder.core.events.EventUI
import com.ghitai.petfinder.core.events.IdleUI
import com.ghitai.petfinder.core.events.LoadingUI
import com.ghitai.petfinder.core.events.SuccessUI

class PetEventStateMapper : EventViewStateMapper<Map<PetActions, EventUI>, PetEventState> {

    override fun toEventState(input: Map<PetActions, EventUI>): PetEventState {
        return PetEventState(
            petIdleUI = input[PetActions.PET_DISPLAY] as? IdleUI,
            petLoadingUI = input[PetActions.PET_DISPLAY] as? LoadingUI,
            petErrorUI = input[PetActions.PET_DISPLAY] as? ErrorUI,
            petSuccessUI = input[PetActions.PET_DISPLAY] as? SuccessUI
        )
    }
}
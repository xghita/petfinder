package com.ghitai.petfinder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ghitai.petfinder.ui.event.PetActions
import com.ghitai.petfinder.core.base.BaseViewModel
import com.ghitai.petfinder.ui.event.PetEventState
import com.ghitai.petfinder.ui.event.PetEventStateMapper

abstract class PetViewModel : BaseViewModel<PetActions>() {

    private val eventStateMapper = PetEventStateMapper()

    fun eventsState(): LiveData<PetEventState> {
        return getEvents().map { eventStateMapper.toEventState(it) }
    }

}

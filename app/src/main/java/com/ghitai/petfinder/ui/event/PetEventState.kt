package com.ghitai.petfinder.ui.event

import com.ghitai.petfinder.core.events.ErrorUI
import com.ghitai.petfinder.core.events.IdleUI
import com.ghitai.petfinder.core.events.LoadingUI
import com.ghitai.petfinder.core.events.SuccessUI

data class PetEventState(
    val petIdleUI: IdleUI? = null,
    val petLoadingUI: LoadingUI? = null,
    val petErrorUI: ErrorUI? = null,
    val petSuccessUI: SuccessUI? = null,
)
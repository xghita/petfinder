package com.ghitai.petfinder.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ghitai.petfinder.provider.PetProvider
import com.ghitai.petfinder.ui.PetViewModel
import com.ghitai.petfinder.ui.event.PetActions
import com.ghitai.petfinder.core.events.ErrorUI
import com.ghitai.petfinder.core.events.IdleUI
import com.ghitai.petfinder.core.events.LoadingUI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PetDetailViewModel(petId: Int, petProvider: PetProvider) : PetViewModel() {

    private val detailViewStateMapper = PetDetailViewStateMapper()
    private val petDetailViewState = MutableLiveData<PetDetailViewState>()

    init {
        addDisposable(petProvider.getPetDetail(petId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { publishEvent(PetActions.PET_DISPLAY, LoadingUI()) }
            .doAfterSuccess { publishEvent(PetActions.PET_DISPLAY, IdleUI) }
            .subscribe({
                petDetailViewState.postValue(detailViewStateMapper.toViewState(it))
            }, {
                publishEvent(
                    PetActions.PET_DISPLAY, ErrorUI(description = it.localizedMessage)
                )
            })
        )
    }

    fun petDetailViewState(): LiveData<PetDetailViewState> {
        return petDetailViewState
    }

}
package com.ghitai.petfinder.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ghitai.petfinder.provider.PetProvider
import com.ghitai.petfinder.ui.PetViewModel
import com.ghitai.petfinder.ui.event.PetActions
import com.ghitai.petfinder.core.events.ErrorUI
import com.ghitai.petfinder.core.events.LoadingUI
import com.ghitai.petfinder.core.events.SuccessUI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PetListViewModel(repoProvider: PetProvider) : PetViewModel() {

    private val listViewStateMapper = PetListViewStateMapper()
    private val petListViewState = MutableLiveData<PetListViewState>()

    init {
        addDisposable(repoProvider.getPetList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                publishEvent(PetActions.PET_DISPLAY, LoadingUI())
            }
            .subscribe({
                petListViewState.postValue(listViewStateMapper.toViewState(it))
                publishEvent(PetActions.PET_DISPLAY, SuccessUI())
            }, {
                publishEvent(
                    PetActions.PET_DISPLAY, ErrorUI(description = it.localizedMessage)
                )
            })
        )
    }

    fun petListViewState(): LiveData<PetListViewState> {
        return petListViewState
    }
}
package com.ghitai.petfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ghitai.petfinder.core.events.ErrorUI
import com.ghitai.petfinder.core.events.IdleUI
import com.ghitai.petfinder.data.pet.Pet
import com.ghitai.petfinder.provider.PetProvider
import com.ghitai.petfinder.ui.detail.PetDetailViewModel
import com.ghitai.petfinder.ui.detail.PetDetailViewState
import com.ghitai.petfinder.ui.event.PetEventState
import com.ghitai.petfinder.util.TestObserver
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.`when`

class PetDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: PetDetailViewModel
    private val petProvider = mock(PetProvider::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupBaseClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory {
                        PetDetailViewModel(anyInt(), petProvider)
                    }
                }
            )
        }
    }

    @Test
    fun `test view state success`() {
         val petDetail = Pet.Detail()

        `when`(petProvider.getPetDetail(anyInt())).thenReturn(Single.just(petDetail))
        viewModel = getKoin().get()

        val viewStateLiveData = viewModel.petDetailViewState()

        val viewStateTestObserver = TestObserver.test(viewStateLiveData)
        viewStateTestObserver.assertHistorySize(1)
            .assertValue { viewState ->
                viewState == PetDetailViewState(
                    name = petDetail.name,
                    breed = petDetail.breed,
                    size = petDetail.size,
                    gender = petDetail.gender,
                    status = petDetail.status,
                    distance = petDetail.distance,
                    photoUrl = petDetail.photoUrl,
                )
            }
        viewStateLiveData.removeObserver(viewStateTestObserver)

        val eventState = viewModel.eventsState()
        val eventStateTestObserver = TestObserver.test(eventState)
        eventStateTestObserver.assertHistorySize(1)
            .assertValue(PetEventState(petIdleUI = IdleUI))
        eventState.removeObserver(eventStateTestObserver)
    }


    @Test
    fun `test view state error`() {
        val error = Throwable("Error")

        `when`(petProvider.getPetDetail(anyInt())).thenReturn(Single.error(error))
        viewModel = getKoin().get()

        val viewStateLiveData = viewModel.petDetailViewState()

        val viewStateTestObserver = TestObserver.test(viewStateLiveData)
        viewStateTestObserver.assertNoValue()
        viewStateLiveData.removeObserver(viewStateTestObserver)

        val eventState = viewModel.eventsState()
        val eventStateTestObserver = TestObserver.test(eventState)
        eventStateTestObserver.assertHistorySize(1)
            .assertValue(PetEventState(petErrorUI = ErrorUI(description = error.localizedMessage)))
        eventState.removeObserver(eventStateTestObserver)
    }

    @After
    fun tearDown() {
        stopKoin()
        reset(petProvider)
    }
}

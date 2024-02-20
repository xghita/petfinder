package com.ghitai.petfinder

import android.app.Application
import com.ghitai.petfinder.di.networkModule
import com.ghitai.petfinder.di.persistenceModule
import com.ghitai.petfinder.di.providerModule
import com.ghitai.petfinder.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PetApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@PetApp)
            modules(
                listOf(
                    viewModels,
                    networkModule,
                    persistenceModule,
                    providerModule
                )
            )
        }
    }
}
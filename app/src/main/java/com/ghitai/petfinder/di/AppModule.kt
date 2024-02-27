package com.ghitai.petfinder.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ghitai.petfinder.data.local.LocalDataStorage
import com.ghitai.petfinder.data.local.LocalDataStorageImpl
import com.ghitai.petfinder.data.local.room.PET_DATABASE
import com.ghitai.petfinder.data.local.room.PetDetailDatabase
import com.ghitai.petfinder.data.network.HeaderInterceptor
import com.ghitai.petfinder.data.network.PetApi
import com.ghitai.petfinder.data.network.TokenAuthenticator
import com.ghitai.petfinder.provider.PetProvider
import com.ghitai.petfinder.provider.PetProviderImpl
import com.ghitai.petfinder.ui.detail.PetDetailViewModel
import com.ghitai.petfinder.ui.list.PetListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val viewModels = module {
    viewModel { (petId: Int) -> PetDetailViewModel(petId, get()) }
    viewModel { PetListViewModel(get()) }
}

val networkModule = module {

    single { createOkHttpClient(get(), get()) }
    single { createRetrofit(get()) }
    single { createApiService(get()) }
    single { HeaderInterceptor(get()) }
    single { TokenAuthenticator(get()) { get<PetApi>() } }
}

val persistenceModule = module {
    single {
        Room.databaseBuilder(androidApplication(), PetDetailDatabase::class.java, PET_DATABASE)
            .build()
    }
    single { get<PetDetailDatabase>().petDao() }

    single<LocalDataStorage> { LocalDataStorageImpl(get()) }

    single { createEncryptedSharedPrefs(get()) }
}

val providerModule = module {
    single<PetProvider> { PetProviderImpl(get(), get()) }
}


fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.petfinder.com/v2/")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun createOkHttpClient(tokenAuthenticator: TokenAuthenticator, headerInterceptor: HeaderInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .authenticator(tokenAuthenticator)
        .build()
}

fun createApiService(retrofit: Retrofit): PetApi = retrofit.create(PetApi::class.java)

fun createEncryptedSharedPrefs(context: Context): SharedPreferences {
    val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        context,
        "encrypted_shared_prefs",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
}

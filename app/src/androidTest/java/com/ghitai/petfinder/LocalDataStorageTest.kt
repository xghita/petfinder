package com.ghitai.petfinder

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ghitai.petfinder.data.local.LocalDataStorage
import com.ghitai.petfinder.data.local.LocalDataStorageImpl
import com.ghitai.petfinder.data.local.room.PetDetailDatabase
import com.ghitai.petfinder.data.local.room.PetDetailEntity
import com.ghitai.petfinder.data.pet.Pet
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataStorageTest {

    private lateinit var database: PetDetailDatabase
    private lateinit var localDataStorage: LocalDataStorage

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PetDetailDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localDataStorage = LocalDataStorageImpl(database.petDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_set_and_retrieve_object_to_local_database() {
        val petId = 123
        val petDetail = Pet.Detail()
        val petEntity = PetDetailEntity(
            id = petId,
            name = petDetail.name,
            breed = petDetail.breed,
            size = petDetail.size,
            gender = petDetail.gender,
            status = petDetail.status,
            distance = petDetail.distance,
            photoUrl = petDetail.photoUrl
        )
        localDataStorage.insertPet(petEntity)

        val retrieved = localDataStorage.getPetDetail(petId).blockingGet()
        assertEquals(petDetail, retrieved)
    }
}

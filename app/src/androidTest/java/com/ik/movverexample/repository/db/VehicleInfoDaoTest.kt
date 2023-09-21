package com.ik.movverexample.repository.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ik.movverexample.repository.model.VehicleInfoEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleInfoDaoTest {

    private lateinit var db: VehicleInfoDb
    private lateinit var dao: VehicleInfoDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, VehicleInfoDb::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.vehicleInfoDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertAndGetAll() = runBlocking {
        val vehicles = listOf(
            VehicleInfoEntity("plate1", "John Doe", 40.0, 10.0, "Location A", "url1", "time1"),
            VehicleInfoEntity("plate2", "Jane Doe", 50.0, 20.0, "Location B", "url2", "time2")
        )

        dao.insertAll(vehicles)

        val result = dao.getAll().first()
        assertThat(result).isEqualTo(vehicles)
    }
}
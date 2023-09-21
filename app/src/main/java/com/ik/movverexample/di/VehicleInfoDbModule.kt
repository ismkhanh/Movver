package com.ik.movverexample.di

import android.content.Context
import androidx.room.Room
import com.ik.movverexample.repository.db.VehicleInfoDao
import com.ik.movverexample.repository.db.VehicleInfoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val VEHICLE_INFO_DB_NAME = "vehicle_info"

@Module
@InstallIn(SingletonComponent::class)
object VehicleInfoDbModule {

    @Provides
    @Singleton
    fun provideVehicleInfoDb(@ApplicationContext context: Context): VehicleInfoDb {
        return Room.databaseBuilder(
            context,
            VehicleInfoDb::class.java,
            VEHICLE_INFO_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideVehicleInfoDao(vehicleInfoDb: VehicleInfoDb): VehicleInfoDao {
        return vehicleInfoDb.vehicleInfoDao()
    }
}
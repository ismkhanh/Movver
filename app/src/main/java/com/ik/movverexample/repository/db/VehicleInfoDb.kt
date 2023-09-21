package com.ik.movverexample.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ik.movverexample.repository.model.VehicleInfoEntity

@Database(entities = [VehicleInfoEntity::class], version = 1)
abstract class VehicleInfoDb : RoomDatabase() {
    abstract fun vehicleInfoDao(): VehicleInfoDao
}
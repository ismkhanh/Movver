package com.ik.movverexample.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ik.movverexample.di.VEHICLE_INFO_DB_NAME
import com.ik.movverexample.repository.model.VehicleInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleInfoDao {

    @Query("SELECT * FROM $VEHICLE_INFO_DB_NAME")
    fun getAll(): Flow<List<VehicleInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vehicles: List<VehicleInfoEntity>)

}
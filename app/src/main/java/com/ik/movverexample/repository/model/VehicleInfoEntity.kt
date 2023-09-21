package com.ik.movverexample.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.ik.movverexample.di.VEHICLE_INFO_DB_NAME
import com.ik.movverexample.utils.TimeUtils


@Entity(tableName = VEHICLE_INFO_DB_NAME)
data class VehicleInfoEntity(
    @PrimaryKey @ColumnInfo(name = "plate_no") val plateNo: String,
    @ColumnInfo(name = "driver_name") val driverName: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "image_url") val imageURL: String,
    @ColumnInfo(name = "last_updated") val lastUpdated: String
)


fun VehicleInfoEntity?.latLng(defaultLatLng: LatLng = LatLng(0.0, 0.0)): LatLng {
    return this?.let { LatLng(latitude, longitude) } ?: defaultLatLng
}

val VehicleInfoEntity.lastUpdatedHumanReadable: String
    get() = TimeUtils.getRelativeTime(this.lastUpdated)
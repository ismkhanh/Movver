package com.ik.movverexample.utils

import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.repository.model.VehicleInfoResponse

object VehicleInfoMapper {

    fun fromResponseToEntity(response: VehicleInfoResponse): VehicleInfoEntity {
        return VehicleInfoEntity(
            plateNo = response.plateNo,
            driverName = response.driverName,
            latitude = response.latitude,
            longitude = response.longitude,
            location = response.location,
            imageURL = response.imageURL,
            lastUpdated = response.lastUpdated,
        )
    }

}
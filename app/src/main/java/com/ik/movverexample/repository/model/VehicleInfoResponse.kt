package com.ik.movverexample.repository.model

import com.google.android.gms.maps.model.LatLng
import com.ik.movverexample.utils.TimeUtils
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehicleInfoResponse(
    @Json(name = "plateNo") val plateNo: String,
    @Json(name = "driverName") val driverName: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lng") val longitude: Double,
    @Json(name = "location") val location: String,
    @Json(name = "imageURL") val imageURL: String,
    @Json(name = "lastUpdated") val lastUpdated: String
)

val VehicleInfoResponse.lastUpdatedHumanReadable: String
    get() = TimeUtils.getRelativeTime(this.lastUpdated)




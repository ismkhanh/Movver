package com.ik.movverexample.repository.network

import com.ik.movverexample.repository.model.VehicleInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface VehicleInfoApi {

    @GET("/api/candidate")
    suspend fun fetchVehicleInfo(): Response<List<VehicleInfoResponse>>

}
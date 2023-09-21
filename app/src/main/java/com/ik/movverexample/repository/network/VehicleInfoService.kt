package com.ik.movverexample.repository.network

import com.ik.movverexample.repository.model.VehicleInfoResponse
import com.ik.movverexample.utils.MovverDispatchers
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.ik.movverexample.utils.Result

interface VehicleInfoService {
    suspend fun fetchVehicleInfo(): Flow<Result<List<VehicleInfoResponse>>>
}

class VehicleInfoServiceImpl @Inject constructor(
    private val api: VehicleInfoApi,
    private val dispatchers: MovverDispatchers
): VehicleInfoService {

    override suspend fun fetchVehicleInfo(): Flow<Result<List<VehicleInfoResponse>>> {
        return flow {
            try {
                val response = api.fetchVehicleInfo()
                if (response.isSuccessful) {
                    val vehicleInfoList = response.body()
                    if (vehicleInfoList != null) {
                        emit(Result.Success(vehicleInfoList))
                    } else {
                        emit(Result.Error("Vehicle list is empty"))
                    }
                } else {
                    emit(Result.Error("Failed to fetch data: ${response.errorBody()?.string()}"))
                }
            } catch (e: IOException) {
                emit(Result.Error(e.message ?: "IO Exception while fetching list"))
            }
        }.flowOn(dispatchers.io)
    }

}
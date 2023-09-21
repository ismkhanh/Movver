package com.ik.movverexample.repository

import com.ik.movverexample.repository.db.VehicleInfoDb
import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.repository.network.VehicleInfoService
import com.ik.movverexample.utils.MovverDispatchers
import com.ik.movverexample.utils.NetworkUtils
import com.ik.movverexample.utils.VehicleInfoMapper
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.ik.movverexample.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn


interface VehicleInfoRepository {
    suspend fun getVehicleInfoList(): Flow<Result<List<VehicleInfoEntity>>>
}

class VehicleInfoRepositoryImpl @Inject constructor(
    private val apiService: VehicleInfoService,
    private val database: VehicleInfoDb,
    private val networkUtils: NetworkUtils,
    private val dispatchers: MovverDispatchers,
) : VehicleInfoRepository {
    override suspend fun getVehicleInfoList(): Flow<Result<List<VehicleInfoEntity>>> {
        return flow {
            if (networkUtils.hasNetwork()) {
                apiService.fetchVehicleInfo().collect { result ->
                    when(result) {
                        is Result.Success -> {
                            val entityList = result.data.map { VehicleInfoMapper.fromResponseToEntity(it) }
                            database.vehicleInfoDao().insertAll(entityList)
                            emit(Result.Success(entityList))
                        }
                        is Result.Error -> emit(result)
                    }
                }
            } else {
                val entityList = database.vehicleInfoDao().getAll().first()
                if (entityList.isNotEmpty()) {
                    emit(Result.Success(entityList))
                } else {
                    emit(Result.Error("No data available"))
                }
            }
        }.flowOn(dispatchers.io)
    }
}
package com.ik.movverexample.repository

import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.utils.Result
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Module
@InstallIn(SingletonComponent::class)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideVehicleInfoRepository(): VehicleInfoRepository = FakeVehicleInfoRepository()
}

class FakeVehicleInfoRepository : VehicleInfoRepository {

    private val mockVehicleInfoEntityLists = listOf(
        VehicleInfoEntity(
            plateNo="X19599",
            driverName="Jane Liam",
            latitude=25.357119,
            longitude=55.391068,
            location="Rolla, Sharjah, the UAE",
            imageURL="https://i.picsum.photos/id/583/200/300.jpg",
            lastUpdated="2023-09-21T12:38:23+00:00"
        ),
        VehicleInfoEntity(
            plateNo="O41291",
            driverName="John Noah",
            latitude=24.058611,
            longitude=55.7775,
            location="Jebel Hafeet Mountain Road, UAE",
            imageURL="https://i.picsum.photos/id/177/200/300.jpg",
            lastUpdated="2023-09-21T14:33:23+00:00")
    )

    override suspend fun getVehicleInfoList(): Flow<Result<List<VehicleInfoEntity>>> {
        return flow {
            emit(Result.Success(mockVehicleInfoEntityLists))
        }
    }

}

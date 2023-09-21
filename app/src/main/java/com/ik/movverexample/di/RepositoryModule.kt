package com.ik.movverexample.di

import com.ik.movverexample.repository.VehicleInfoRepository
import com.ik.movverexample.repository.VehicleInfoRepositoryImpl
import com.ik.movverexample.repository.network.VehicleInfoService
import com.ik.movverexample.repository.network.VehicleInfoServiceImpl
import com.ik.movverexample.utils.NetworkUtils
import com.ik.movverexample.utils.NetworkUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindVehicleInfoService(impl: VehicleInfoServiceImpl): VehicleInfoService

    @Binds
    abstract fun bindVehicleInfoRepository(impl: VehicleInfoRepositoryImpl): VehicleInfoRepository

    @Binds
    abstract fun bindNetworkUtils(impl: NetworkUtilsImpl): NetworkUtils

}
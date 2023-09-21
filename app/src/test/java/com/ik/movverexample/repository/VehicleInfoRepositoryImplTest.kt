package com.ik.movverexample.repository

import com.ik.movverexample.repository.db.VehicleInfoDao
import com.ik.movverexample.repository.db.VehicleInfoDb
import com.ik.movverexample.repository.model.VehicleInfoResponse
import com.ik.movverexample.repository.network.VehicleInfoService
import com.ik.movverexample.utils.MovverDispatchers
import com.ik.movverexample.utils.NetworkUtils
import com.ik.movverexample.utils.VehicleInfoMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import com.ik.movverexample.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.mockkStatic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VehicleInfoRepositoryImplTest {

    private lateinit var repository: VehicleInfoRepositoryImpl
    private val apiService: VehicleInfoService = mockk()
    private val database: VehicleInfoDb = mockk()
    private val dao: VehicleInfoDao = mockk()
    private val networkUtils: NetworkUtils = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = object :  MovverDispatchers {
        override val main: CoroutineDispatcher
            get() = testDispatcher
        override val default: CoroutineDispatcher
            get() = testDispatcher
        override val io: CoroutineDispatcher
            get() = testDispatcher

    }

    val mockVehicleInfoResponse = listOf (VehicleInfoResponse(
        plateNo = "ABC123",
        driverName = "John Doe",
        latitude = 0.0,
        longitude = 0.0,
        location = "test",
        imageURL = "",
        lastUpdated = "2023-09-21T02:26:19+00:00"
    ))

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        coEvery { database.vehicleInfoDao() } returns dao
        repository = VehicleInfoRepositoryImpl(apiService, database, networkUtils, dispatchers)
    }

    @Test
    fun `test fetch vehicle info with network`() = runTest(testDispatcher) {
        val response = mockVehicleInfoResponse
        val entityList = response.map { VehicleInfoMapper.fromResponseToEntity(it) }

        coEvery { networkUtils.hasNetwork() } returns true
        coEvery { apiService.fetchVehicleInfo() } returns flowOf(Result.Success(response))
        coEvery { dao.insertAll(any()) } returns Unit

        val result = repository.getVehicleInfoList().first()

        when (result) {
            is Result.Success -> assertEquals(entityList.size, result.data.size)
            is Result.Error -> throw AssertionError("Expected Success, but got Error")
        }
    }

    @Test
    fun `test fetch vehicle info without network and only database data`() = runTest(testDispatcher) {
        val entityList = mockVehicleInfoResponse.map { VehicleInfoMapper.fromResponseToEntity(it) }

        coEvery { networkUtils.hasNetwork() } returns false
        coEvery { dao.getAll() } returns flowOf(entityList)

        val result = repository.getVehicleInfoList().first()

        when (result) {
            is Result.Success -> assertEquals(entityList.size, result.data.size)
            is Result.Error -> throw AssertionError("Expected Success, but got Error")
        }
    }

    @Test
    fun `test fetch vehicle info without network and no database data`() = runTest(testDispatcher) {
        mockkStatic(NetworkUtils::class)
        coEvery { networkUtils.hasNetwork() } returns false
        coEvery { dao.getAll() } returns flowOf(emptyList())

        val result = repository.getVehicleInfoList().first()

        when (result) {
            is Result.Success -> throw AssertionError("Expected Error, but got Success")
            is Result.Error -> assertEquals("No data available", result.errorMsg)
        }
    }

}
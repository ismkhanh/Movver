package com.ik.movverexample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ik.movverexample.repository.VehicleInfoRepository
import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.ui.MovverAction
import com.ik.movverexample.ui.MovverViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.ik.movverexample.utils.Result
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.rules.TestRule


@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MovverViewModelTest {

    private val mockRepository = mockk<VehicleInfoRepository>()
    private lateinit var viewModel: MovverViewModel

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val mockVehicleInfoEntityLists = listOf(
        VehicleInfoEntity(
            plateNo="X 19599",
            driverName="Jane Liam",
            latitude=25.357119,
            longitude=55.391068,
            location="Rolla, Sharjah, the UAE",
            imageURL="https://i.picsum.photos/id/583/200/300.jpg",
            lastUpdated="2023-05-01"
        ),
        VehicleInfoEntity(
            plateNo="O 41291",
            driverName="John Noah",
            latitude=24.058611,
            longitude=55.7775,
            location="Jebel Hafeet Mountain Road, UAE",
            imageURL="https://i.picsum.photos/id/177/200/300.jpg",
            lastUpdated="2023-05-02")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test fetch vehicle info on init`()  {
        val dummyData = mockVehicleInfoEntityLists
        coEvery { mockRepository.getVehicleInfoList() } returns flowOf(Result.Success(dummyData))

        viewModel = MovverViewModel(mockRepository)

        assertEquals(dummyData, viewModel.uiState.value.data)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `test search vehicle info`()  {
        val dummyData = mockVehicleInfoEntityLists
        coEvery { mockRepository.getVehicleInfoList() } returns flowOf(Result.Success(dummyData))

        viewModel = MovverViewModel(mockRepository)
        viewModel.handleAction(MovverAction.OnSearch("Jane"))

        assertEquals(1, viewModel.uiState.value.filteredData.size)
        assertEquals("Jane Liam", viewModel.uiState.value.filteredData[0].driverName)
    }

    @Test
    fun `test sort vehicle info`() {
        val dummyData = mockVehicleInfoEntityLists
        coEvery { mockRepository.getVehicleInfoList() } returns flowOf(Result.Success(dummyData))
        viewModel = MovverViewModel(mockRepository)  // Re-initialize viewModel to use the mocked data

        viewModel.handleAction(MovverAction.Sort)

        assertEquals("John Noah", viewModel.uiState.value.data[0].driverName)
        assertEquals("Jane Liam", viewModel.uiState.value.data[1].driverName)
    }
}
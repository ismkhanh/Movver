package com.ik.movverexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ik.movverexample.repository.VehicleInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.ik.movverexample.utils.Result


@HiltViewModel
class MovverViewModel @Inject constructor(
    private val repository: VehicleInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovverState(isLoading = true))
    val uiState: StateFlow<MovverState> = _uiState.asStateFlow()

    init {
        fetchVehicleInfoList()
    }

    private fun fetchVehicleInfoList() {
        viewModelScope.launch {
            _uiState.value = MovverState(isLoading = true)
            repository.getVehicleInfoList().collect { result ->
                when(result) {
                    is Result.Success -> _uiState.value = MovverState(
                        data = result.data,
                        filteredData = result.data,
                        isLoading = false
                    ).also {
                        println("vehicleinfo domain model: ${it.data}")
                    }
                    is Result.Error -> _uiState.value = MovverState(
                        error = result.errorMsg,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun handleAction(action: MovverAction) {
        when(action) {
            is MovverAction.OnSearch -> {
                searchVehicleInfoList(action.query)
            }
            is MovverAction.Sort -> {
                sortVehicleInfoList()
            }
        }
    }

    private fun searchVehicleInfoList(query: String) {
        val filteredList = uiState.value.data.filter {
            it.driverName.contains(query, ignoreCase = true) ||
                    it.plateNo.contains(query, ignoreCase = true) ||
                    it.location.contains(query, ignoreCase = true)
        }

        _uiState.value = _uiState.value.copy(filteredData = filteredList)
    }

    private fun sortVehicleInfoList() {
        val originalListSorted = uiState.value.data.sortedByDescending { it.lastUpdated }
        val filteredListSorted = uiState.value.filteredData.sortedByDescending { it.lastUpdated }
        _uiState.value = _uiState.value.copy(data = originalListSorted, filteredData = filteredListSorted)
    }
}
package com.ik.movverexample.ui

import com.ik.movverexample.repository.model.VehicleInfoEntity

data class MovverState(
    val data: List<VehicleInfoEntity> = emptyList(),
    val filteredData: List<VehicleInfoEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

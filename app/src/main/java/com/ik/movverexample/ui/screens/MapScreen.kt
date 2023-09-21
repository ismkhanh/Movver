package com.ik.movverexample.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.repository.model.latLng
import com.ik.movverexample.ui.MovverState
import com.ik.movverexample.ui.widgets.CardType
import com.ik.movverexample.ui.widgets.VehicleInfoCard

const val defaultZoom = 18f

@Composable
fun MapScreen(state: MovverState) {

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error)
            }
        }
        state.filteredData != null -> {
            val isMapInitialized = remember { mutableStateOf(false) }
            val listState = rememberLazyListState()
            val visibleVehicleInfoEntity: MutableState<VehicleInfoEntity> = remember { mutableStateOf (state.data.first()) }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(visibleVehicleInfoEntity.value.latLng(), defaultZoom)
            }

            LaunchedEffect(listState.firstVisibleItemIndex) {
                val currentItem = state.filteredData.getOrNull(listState.firstVisibleItemIndex)
                currentItem?.let { visibleVehicleInfoEntity.value = it }
            }

            Box(Modifier.fillMaxSize().testTag("map")) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(zoomControlsEnabled = false),
                    onMapLoaded = { isMapInitialized.value = true}
                ) {
                    Marker(
                        state = MarkerState(position = visibleVehicleInfoEntity.value.latLng()),
                        title = visibleVehicleInfoEntity.value.location
                    )
                }

                if (isMapInitialized.value) {
                    UpdateCameraPosition(visibleVehicleInfoEntity, cameraPositionState)
                }

                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 56.dp)
                ) {
                    items(state.filteredData) { vehicleInfo ->
                        VehicleInfoCard(vehicleInfo, CardType.ROW) {
                            visibleVehicleInfoEntity.value = vehicleInfo
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UpdateCameraPosition(
    visibleVehicleInfoEntity: MutableState<VehicleInfoEntity>,
    cameraPositionState: CameraPositionState
) {
    val newPosition = CameraPosition.fromLatLngZoom(visibleVehicleInfoEntity.value.latLng(), defaultZoom)
    cameraPositionState.move(CameraUpdateFactory.newCameraPosition(newPosition))
}


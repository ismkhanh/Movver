package com.ik.movverexample.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.ik.movverexample.ui.MovverAction
import com.ik.movverexample.ui.MovverState
import com.ik.movverexample.ui.widgets.CardType
import com.ik.movverexample.ui.widgets.VehicleInfoCard

@Composable
fun ListScreen(state: MovverState) {
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
                modifier = Modifier.fillMaxSize().testTag("list"),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = state.error)
            }
        }
        state.data != null -> {
            LazyColumn {
                items(state.filteredData) { vehicleInfo ->
                    VehicleInfoCard(vehicleInfo, CardType.COLUMN)
                }
            }
        }
    }
}
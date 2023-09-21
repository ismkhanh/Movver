package com.ik.movverexample.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.repository.model.lastUpdatedHumanReadable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleInfoCard(vehicleInfoEntity: VehicleInfoEntity, cardType: CardType, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).testTag(vehicleInfoEntity.plateNo),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SubcomposeAsyncImage(
                modifier = Modifier.size(55.dp),
                model = vehicleInfoEntity.imageURL,
                contentDescription = ""
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator()
                }  else if (state is AsyncImagePainter.State.Error) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                }else {
                    SubcomposeAsyncImageContent()
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoCardText(text = "Plate No: ${vehicleInfoEntity.plateNo}")
                InfoCardText(text = "Driver Name: ${vehicleInfoEntity.driverName}")
                InfoCardText(text = "Location: ${vehicleInfoEntity.location.cardTypeCompatible(cardType)}")
                InfoCardText(text = "Last Update: ${vehicleInfoEntity.lastUpdatedHumanReadable}")
            }
        }
    }
}

private fun String.cardTypeCompatible(cardType: CardType): String {
    val size = this.length
    return when (cardType) {
        CardType.COLUMN -> this
        CardType.ROW -> this.take(25)
            .plus(if (size > 25) "..." else "" ) // todo the value 25 can be improved based on screen size
    }
}

enum class CardType {
    COLUMN, ROW
}

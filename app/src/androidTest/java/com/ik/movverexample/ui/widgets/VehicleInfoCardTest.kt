package com.ik.movverexample.ui.widgets

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ik.movverexample.repository.model.VehicleInfoEntity
import com.ik.movverexample.utils.TimeUtils
import java.util.*
import org.junit.Rule
import org.junit.Test

class VehicleInfoCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun vehicleInfoCard_displaysLastUpdatedHumanReadable() {
        val mockedTimeUtils = object : TimeUtils {
            override fun getRelativeTime(timeString: String, currentTime: Date): String {
               return "Mocked Relative Time"
            }
        }

        val vehicleInfoEntity = VehicleInfoEntity(
            plateNo = "1234XYZ",
            driverName = "Test Driver",
            latitude = 0.0,
            longitude = 0.0,
            location = "Test Location",
            imageURL = "",
            lastUpdated = "2022-01-01T10:10:00Z"
        )

        composeTestRule.setContent {
            VehicleInfoCard(vehicleInfoEntity, CardType.COLUMN, timeUtils = mockedTimeUtils)
        }

        composeTestRule.onNodeWithTag(vehicleInfoEntity.plateNo).assertIsDisplayed()

        composeTestRule.onNodeWithText("Mocked Relative Time", substring = true).assertExists()
    }
}
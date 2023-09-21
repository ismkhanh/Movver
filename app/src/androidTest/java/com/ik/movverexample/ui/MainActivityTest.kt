package com.ik.movverexample.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ik.movverexample.di.RepositoryModule
import com.ik.movverexample.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationButtons() {
        val listButton = composeTestRule.onNodeWithText("List")
        val mapButton = composeTestRule.onNodeWithText("Map")

        listButton.assertIsDisplayed()
        mapButton.assertIsDisplayed()

        mapButton.performClick()
        val mapScreen = composeTestRule.onNodeWithTag("map")
        mapScreen.assertIsDisplayed()
    }

    @Test
    fun testSearchFunctionality() {
        composeTestRule.onNodeWithText("Search").performTextInput("Jane Liam")

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Jane Liam").assertIsDisplayed()
        composeTestRule.onNodeWithText("John Noah").assertDoesNotExist()
    }

    @Test
    fun testSortFunctionality() {
        composeTestRule.onNodeWithTag("sort").performClick()

        composeTestRule.waitForIdle()

        val firstVehiclePlate = "O41291" // the plateNo of the expected first vehicle after sorting
        val secondVehiclePlate = "X19599" // the plateNo of the expected second vehicle after sorting
        val firstVehiclePosition = composeTestRule.onNodeWithTag(firstVehiclePlate).getUnclippedBoundsInRoot().top
        val secondVehiclePosition = composeTestRule.onNodeWithTag(secondVehiclePlate).getUnclippedBoundsInRoot().top
        assertTrue(firstVehiclePosition < secondVehiclePosition)
    }

}


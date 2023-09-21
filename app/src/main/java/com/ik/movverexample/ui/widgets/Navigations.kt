package com.ik.movverexample.ui.widgets

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ik.movverexample.ui.MovverAction
import com.ik.movverexample.ui.MovverState
import com.ik.movverexample.ui.screens.ListScreen
import com.ik.movverexample.ui.screens.MapScreen

@Composable
fun Navigations(
    navController: NavHostController,
    state: MovverState
) {
    NavHost(navController, startDestination = NavigationItem.List.route) {
        composable(NavigationItem.List.route) {
            ListScreen(state)
        }
        composable(NavigationItem.Map.route) {
            MapScreen(state)
        }
    }
}
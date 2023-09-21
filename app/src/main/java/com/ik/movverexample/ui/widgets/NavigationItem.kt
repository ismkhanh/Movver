package com.ik.movverexample.ui.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, val icon: ImageVector?, var title: String) {
    object List : NavigationItem("List", Icons.Rounded.List, "List")
    object Map : NavigationItem("Map", Icons.Rounded.LocationOn, "Map")
}
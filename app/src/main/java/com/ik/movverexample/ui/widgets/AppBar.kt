package com.ik.movverexample.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.ik.movverexample.ui.widgets.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onSearchAction: (String) -> Unit,
    onSortAction: () -> Unit
) {
    Column() {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Truck Monitor")
            },
            actions = {
                IconButton(
                    modifier = Modifier.testTag("sort"),
                    onClick = onSortAction) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Sort"
                    )
            }}
        )

        SearchBar(onSearchAction = onSearchAction)
    }
}

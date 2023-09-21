package com.ik.movverexample.ui.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun InfoCardText(text: String) {
    Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
}
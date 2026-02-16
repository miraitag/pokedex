package com.miraitag.pokedex.ui.screens.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miraitag.pokedex.ui.common.Heading

@Composable
fun Properties(title: String, property: String) {
    Row {
        Heading(title = title)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = property, style = MaterialTheme.typography.titleLarge)
    }
}
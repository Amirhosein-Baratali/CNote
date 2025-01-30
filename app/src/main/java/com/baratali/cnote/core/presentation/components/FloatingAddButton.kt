package com.baratali.cnote.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FloatingAddButton(
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    FloatingActionButton(
        onClick = onClick, containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = contentDescription
        )
    }
}
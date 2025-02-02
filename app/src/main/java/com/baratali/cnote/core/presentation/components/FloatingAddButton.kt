package com.baratali.cnote.core.presentation.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.baratali.cnote.R

@Composable
fun FloatingAddButton(
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    FloatingActionButton(
        onClick = onClick, containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_note_add),
            contentDescription = contentDescription
        )
    }
}
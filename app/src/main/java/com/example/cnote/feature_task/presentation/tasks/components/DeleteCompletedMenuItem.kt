package com.example.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cnote.R

@Composable
fun DeleteCompletedMenuItem(onDeleteCompletedTasks: () -> Unit) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val deleteCompletedText = stringResource(R.string.delete_completed)
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = deleteCompletedText,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = deleteCompletedText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        onClick = onDeleteCompletedTasks
    )
}
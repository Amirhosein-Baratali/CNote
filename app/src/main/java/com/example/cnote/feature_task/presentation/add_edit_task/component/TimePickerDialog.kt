package com.example.cnote.feature_task.presentation.add_edit_task.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cnote.R
import com.example.cnote.core.presentation.components.CustomText
import com.example.cnote.ui.theme.CNoteTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    dateTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
    onCancel: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = dateTime.hour,
        initialMinute = dateTime.minute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = {
                val selectedTime = LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute
                )
                onTimeSelected(selectedTime)
            }) {
                CustomText(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                CustomText(stringResource(R.string.cancel))
            }
        },
        title = {
            CustomText(stringResource(R.string.select_time))
        },
        text = {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Preview
@Composable
private fun TimePickerDialogPreview() {
    CNoteTheme {
        TimePickerDialog(
            onTimeSelected = {},
            onCancel = {}
        )
    }
}

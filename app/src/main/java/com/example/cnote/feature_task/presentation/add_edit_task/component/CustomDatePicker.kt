package com.example.cnote.feature_task.presentation.add_edit_task.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cnote.R
import com.example.cnote.feature_task.data.util.toEpochMillis
import com.example.cnote.ui.theme.CNoteTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    modifier: Modifier = Modifier,
    defaultDateTime: LocalDateTime = LocalDateTime.now(),
    onDateTimeSelected: (dateTime: LocalDateTime) -> Unit,
    onDismiss: () -> Unit
) {
    val initialMillis = defaultDateTime.toEpochMillis()

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate: LocalDate? by remember { mutableStateOf(null) }

    if (!showTimePicker) {
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { selectedMillis ->
                        selectedDate = Instant.ofEpochMilli(selectedMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        showTimePicker = true
                    }
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    } else {
        selectedDate?.let { date ->
            TimePickerDialog(
                dateTime = defaultDateTime.toLocalTime(),
                onTimeSelected = { time ->
                    onDateTimeSelected(LocalDateTime.of(date, time))
                    onDismiss()
                },
                onCancel = { onDismiss() }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CustomDatePickerPreview() {
    CNoteTheme {
        CustomDatePicker(
            onDateTimeSelected = {},
            onDismiss = {}
        )
    }
}
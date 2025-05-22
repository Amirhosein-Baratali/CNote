package com.baratali.cnote.feature_task.presentation.add_edit_task.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DialogDatePickerColors
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.JalaliDatePicker
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.JalaliDatePickerDefaults
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    modifier: Modifier = Modifier,
    defaultDateTime: LocalDateTime = LocalDateTime.now(),
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    datePickerType: DatePickerType = DatePickerType.JALALI,
    minDate: LocalDateTime? = null,
    maxDate: LocalDateTime? = null,
    minYear: Int = 1400,
    maxYear: Int = 1450,
    showTodayAtStart: Boolean = true,
    colors: DialogDatePickerColors = JalaliDatePickerDefaults.jalaliDatePickerDialogColors()
) {
    when (datePickerType) {
        DatePickerType.JALALI -> {
            JalaliDatePicker(
                defaultDateTime = defaultDateTime,
                onDateTimeSelected = onDateTimeSelected,
                onDismiss = onDismiss,
                minDate = minDate,
                maxDate = maxDate,
                minYear = minYear,
                maxYear = maxYear,
                showTodayAtStart = showTodayAtStart,
                colors = colors
            )
        }

        DatePickerType.GEORGIAN -> {
            GeorgianDatePicker(
                modifier = modifier,
                defaultDateTime = defaultDateTime,
                onDateTimeSelected = onDateTimeSelected,
                onDismiss = onDismiss
            )
        }
    }
}
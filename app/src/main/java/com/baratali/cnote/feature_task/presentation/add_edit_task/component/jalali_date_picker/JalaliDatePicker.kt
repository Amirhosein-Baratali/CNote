package com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.util.JalaliDate
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.TimePickerDialog
import com.baratali.cnote.ui.theme.CNoteTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JalaliDatePicker(
    defaultDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    minDate: LocalDateTime? = null,
    maxDate: LocalDateTime? = null,
    minYear: Int = 1400,
    maxYear: Int = 1450,
    showTodayAtStart: Boolean = false,
    colors: DialogDatePickerColors = JalaliDatePickerDefaults.jalaliDatePickerDialogColors()
) {
    val initialJalali = JalaliDate.fromGregorian(
        gregorianYear = defaultDateTime.year,
        gregorianMonth = defaultDateTime.monthValue,
        gregorianDay = defaultDateTime.dayOfMonth
    )
    val jalaliMinDate = minDate?.let {
        JalaliDate.fromGregorian(it.year, it.monthValue, it.dayOfMonth)
    }
    val jalaliMaxDate = maxDate?.let {
        JalaliDate.fromGregorian(it.year, it.monthValue, it.dayOfMonth)
    }

    var selectedDate by remember { mutableStateOf(initialJalali) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (!showTimePicker) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = colors.dialogBackgroundColor,
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    YearSelector(
                        selectedYear = selectedDate.year,
                        minYear = jalaliMinDate?.year ?: minYear,
                        maxYear = jalaliMaxDate?.year ?: maxYear,
                        onYearSelected = { year ->
                            JalaliDate.adjustDateToValidRange(
                                JalaliDate(year, selectedDate.month, selectedDate.day),
                                jalaliMinDate,
                                jalaliMaxDate
                            )?.let { selectedDate = it }
                        },
                        colors = colors
                    )
                    MonthSelector(
                        selectedMonth = selectedDate.month,
                        allowedMonths = (1..12).filter { month ->
                            val start = JalaliDate(selectedDate.year, month, 1)
                            val end = JalaliDate(
                                selectedDate.year,
                                month,
                                selectedDate.getDaysInJalaliMonth(selectedDate.year, month)
                            )
                            val isAfterMin = jalaliMinDate == null || end >= jalaliMinDate
                            val isBeforeMax = jalaliMaxDate == null || start <= jalaliMaxDate
                            isAfterMin && isBeforeMax
                        },
                        onMonthSelected = { month ->
                            JalaliDate.adjustDateToValidRange(
                                JalaliDate(selectedDate.year, month, selectedDate.day),
                                jalaliMinDate,
                                jalaliMaxDate
                            )?.let { selectedDate = it }
                        },
                        colors = colors
                    )
                }
            },
            text = {
                DaysGrid(
                    selectedDate = selectedDate,
                    onDateClick = { date ->
                        if ((jalaliMinDate == null || date >= jalaliMinDate) && (jalaliMaxDate == null || date <= jalaliMaxDate)) {
                            selectedDate = date
                        }
                    },
                    minDate = jalaliMinDate,
                    maxDate = jalaliMaxDate,
                    colors = colors,
                    showTodayAtStart = showTodayAtStart
                )
            },
            confirmButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Button(
                        onClick = { showTimePicker = true },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.confirmButtonBackgroundColor)
                    ) {
                        CustomText(
                            text = stringResource(R.string.confirm_persian),
                            color = colors.confirmButtonTextColor
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = colors.dismissButtonBackgroundColor)
                    ) {
                        CustomText(
                            text = stringResource(R.string.cancel_persian),
                            color = colors.dismissButtonTextColor
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = {
                            selectedDate = JalaliDate.today()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.todayButtonBackgroundColor)
                    ) {
                        CustomText(
                            text = stringResource(R.string.today_persian),
                            color = colors.todayButtonTextColor
                        )
                    }
                }
            }
        )
    } else {
        TimePickerDialog(
            dateTime = defaultDateTime.toLocalTime(),
            onTimeSelected = { time ->
                val gregorianDate = selectedDate.toGregorian()
                val localDateTime = LocalDateTime.of(
                    gregorianDate[0],
                    gregorianDate[1],
                    gregorianDate[2],
                    time.hour,
                    time.minute
                )
                onDateTimeSelected(localDateTime)
                onDismiss()
            },
            onCancel = onDismiss
        )
    }
}

@PreviewLightDark
@Composable
private fun JalaliDatePickerPreview() {
    CNoteTheme {
        JalaliDatePicker(
            defaultDateTime = LocalDateTime.now(),
            onDateTimeSelected = {},
            onDismiss = {}
        )
    }
}
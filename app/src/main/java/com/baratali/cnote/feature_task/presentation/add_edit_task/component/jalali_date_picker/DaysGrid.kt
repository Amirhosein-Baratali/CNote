package com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.util.JalaliDate
import com.baratali.cnote.ui.theme.CNoteTheme

@Composable
fun DaysGrid(
    selectedDate: JalaliDate,
    onDateClick: (JalaliDate) -> Unit,
    minDate: JalaliDate?,
    maxDate: JalaliDate?,
    colors: DialogDatePickerColors,
    showTodayAtStart: Boolean
) {
    val startOfMonth = JalaliDate(selectedDate.year, selectedDate.month, 1)
    val startDayOfWeek = startOfMonth.getDayOfWeek()
    val daysInMonth = selectedDate.getDaysInJalaliMonth(selectedDate.year, selectedDate.month)
    val today = JalaliDate.today()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            JalaliDate.dayNames.reversed().forEach { // Reverse for RTL
                CustomText(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = colors.dayUnselectedTextColor
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        val totalCells = ((startDayOfWeek + daysInMonth + 6) / 7) * 7
        for (week in 0 until totalCells / 7) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (day in 0..6) {
                    val index = week * 7 + (6 - day) // Adjust for RTL
                    val dayNumber = index - startDayOfWeek + 1
                    if (dayNumber in 1..daysInMonth) {
                        val date = JalaliDate(selectedDate.year, selectedDate.month, dayNumber)
                        val isSelected = date == selectedDate
                        val isToday = date == today
                        val isDisabled =
                            (minDate != null && date < minDate) || (maxDate != null && date > maxDate)

                        val backgroundColor =
                            if (isSelected) colors.daySelectedBackgroundColor else colors.dayUnselectedBackgroundColor
                        val textColor =
                            if (isSelected) colors.daySelectedTextColor else colors.dayUnselectedTextColor

                        Box(
                            modifier = Modifier
                                .weight(1f) // Ensure days fill the width evenly
                                .aspectRatio(1f) // Maintain square shape
                                .border(
                                    width = if (isToday && showTodayAtStart) 2.dp else 0.dp,
                                    color = if (isToday && showTodayAtStart) colors.todayIndicatorColor else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                                .background(backgroundColor)
                                .clickable(enabled = !isDisabled) { onDateClick(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            CustomText(
                                text = "$dayNumber",
                                color = textColor
                            )
                        }
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DaysGridPreview() {
    CNoteTheme {
        DaysGrid(
            selectedDate = JalaliDate.today(),
            onDateClick = {},
            minDate = null,
            maxDate = null,
            colors = JalaliDatePickerDefaults.jalaliDatePickerDialogColors(),
            showTodayAtStart = true
        )
    }
}
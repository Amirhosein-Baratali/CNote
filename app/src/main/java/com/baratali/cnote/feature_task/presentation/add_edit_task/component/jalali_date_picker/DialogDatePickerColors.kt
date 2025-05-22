package com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class DialogDatePickerColors(
    val dialogBackgroundColor: Color,
    val daySelectedBackgroundColor: Color,
    val daySelectedTextColor: Color,
    val dayUnselectedBackgroundColor: Color,
    val dayUnselectedTextColor: Color,
    val todayIndicatorColor: Color,
    val confirmButtonBackgroundColor: Color,
    val confirmButtonTextColor: Color,
    val dismissButtonBackgroundColor: Color,
    val dismissButtonTextColor: Color,
    val gridItemBackgroundColor: Color,
    val gridItemTextColor: Color,
    val gridItemSelectedTextColor: Color,
    val todayButtonBackgroundColor: Color,
    val todayButtonTextColor: Color
)

object JalaliDatePickerDefaults {
    @Composable
    fun jalaliDatePickerDialogColors(
        dialogBackgroundColor: Color = MaterialTheme.colorScheme.background,
        daySelectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        daySelectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        dayUnselectedBackgroundColor: Color = Color.Transparent,
        dayUnselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
        todayIndicatorColor: Color = MaterialTheme.colorScheme.outline,
        confirmButtonBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        confirmButtonTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        dismissButtonBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
        dismissButtonTextColor: Color = MaterialTheme.colorScheme.onSecondary,
        gridItemBackgroundColor: Color = MaterialTheme.colorScheme.surface.copy(0.2f),
        gridItemTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        gridItemSelectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
        todayButtonBackgroundColor: Color = Color.Transparent,
        todayButtonTextColor: Color = MaterialTheme.colorScheme.primary
    ): DialogDatePickerColors {
        return DialogDatePickerColors(
            dialogBackgroundColor = dialogBackgroundColor,
            daySelectedBackgroundColor = daySelectedBackgroundColor,
            daySelectedTextColor = daySelectedTextColor,
            dayUnselectedBackgroundColor = dayUnselectedBackgroundColor,
            dayUnselectedTextColor = dayUnselectedTextColor,
            todayIndicatorColor = todayIndicatorColor,
            confirmButtonBackgroundColor = confirmButtonBackgroundColor,
            confirmButtonTextColor = confirmButtonTextColor,
            dismissButtonBackgroundColor = dismissButtonBackgroundColor,
            dismissButtonTextColor = dismissButtonTextColor,
            gridItemBackgroundColor = gridItemBackgroundColor,
            gridItemTextColor = gridItemTextColor,
            gridItemSelectedTextColor = gridItemSelectedTextColor,
            todayButtonBackgroundColor = todayButtonBackgroundColor,
            todayButtonTextColor = todayButtonTextColor
        )
    }
}

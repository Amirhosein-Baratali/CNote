package com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.core.util.JalaliDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthSelector(
    modifier: Modifier = Modifier,
    selectedMonth: Int,
    allowedMonths: List<Int>,
    onMonthSelected: (Int) -> Unit,
    colors: DialogDatePickerColors
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedMonthText = JalaliDate.monthNames[selectedMonth - 1]
    val months = allowedMonths.chunked(3) // Chunk months into groups of 3
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Calculate the row index of the selected month
    val selectedRowIndex = months.indexOfFirst { row -> selectedMonth in row }
    val totalRows = months.size

    LaunchedEffect(selectedMonth, expanded) {
        if (expanded && selectedRowIndex >= 0) {
            coroutineScope.launch {
                // Calculate the scroll position as a fraction of the total scrollable height
                val scrollFraction = if (totalRows > 0) {
                    selectedRowIndex.toFloat() / totalRows
                } else {
                    0f
                }
                // Animate scroll to the estimated position
                scrollState.animateScrollTo((scrollState.maxValue * scrollFraction).toInt())
            }
        }
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(modifier = modifier) {
            Box(
                modifier = Modifier
                    .border(1.dp, colors.gridItemTextColor, RoundedCornerShape(8.dp))
                    .background(colors.gridItemBackgroundColor, RoundedCornerShape(8.dp))
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = selectedMonthText,
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.gridItemTextColor
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = colors.gridItemTextColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(colors.dialogBackgroundColor)
                    .heightIn(max = 200.dp),
                scrollState = scrollState
            ) {
                months.forEach { monthRow ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        monthRow.forEach { month ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(
                                        if (month == selectedMonth) colors.daySelectedBackgroundColor.copy(
                                            0.5f
                                        )
                                        else colors.gridItemBackgroundColor,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        onMonthSelected(month)
                                        expanded = false
                                    }
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomText(
                                    text = JalaliDate.monthNames[month - 1],
                                    color = if (month == selectedMonth) colors.gridItemSelectedTextColor
                                    else colors.gridItemTextColor,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        // Add empty boxes if the row has fewer than 3 items
                        repeat(3 - monthRow.size) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
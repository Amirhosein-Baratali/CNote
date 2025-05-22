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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baratali.cnote.core.presentation.components.CustomText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearSelector(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    minYear: Int,
    maxYear: Int,
    onYearSelected: (Int) -> Unit,
    colors: DialogDatePickerColors
) {
    var expanded by remember { mutableStateOf(false) }
    val years = (maxYear downTo minYear).toList().chunked(3)
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val selectedRowIndex = years.indexOfFirst { row -> selectedYear in row }
    val totalRows = years.size

    LaunchedEffect(selectedYear, expanded) {
        if (expanded && selectedRowIndex >= 0) {
            coroutineScope.launch {
                val maxScrollValue = scrollState.maxValue.toFloat()
                val scrollFraction = if (totalRows > 0) {
                    selectedRowIndex.toFloat() / totalRows
                } else {
                    0f
                }
                scrollState.animateScrollTo((maxScrollValue * scrollFraction).toInt())
            }
        }
    }

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
                    text = "$selectedYear",
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
            years.forEach { yearRow ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    yearRow.forEach { year ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .background(
                                    if (year == selectedYear) colors.daySelectedBackgroundColor.copy(
                                        0.5f
                                    )
                                    else colors.gridItemBackgroundColor,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp)
                                .clickable {
                                    onYearSelected(year)
                                    expanded = false
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomText(
                                text = "$year",
                                color = if (year == selectedYear) colors.gridItemSelectedTextColor
                                else colors.gridItemTextColor,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    // Add empty boxes if the row has fewer than 3 items
                    repeat(3 - yearRow.size) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
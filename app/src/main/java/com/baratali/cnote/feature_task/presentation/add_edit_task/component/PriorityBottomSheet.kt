package com.baratali.cnote.feature_task.presentation.add_edit_task.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.components.CustomText
import com.baratali.cnote.feature_task.data.data_source.model.TaskPriority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityBottomSheet(
    selectedPriority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var sliderValue by remember { mutableFloatStateOf(selectedPriority.value.toFloat()) }
    var isDragging by remember { mutableStateOf(false) }

    val currentPriority = TaskPriority.fromValue(sliderValue.toInt())
    val priorities = TaskPriority.values()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                text = stringResource(R.string.select_priority),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = sliderValue,
                thumb = {
                    Icon(
                        painter = painterResource(R.drawable.ic_flag),
                        tint = currentPriority.color,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    sliderValue = it
                    isDragging = true
                },
                onValueChangeFinished = {
                    isDragging = false
                    onPrioritySelected(TaskPriority.fromValue(sliderValue.toInt()))
                },
                valueRange = 1f..priorities.size.toFloat(),
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = currentPriority.color,
                    activeTrackColor = currentPriority.color
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            CustomText(
                text = currentPriority.name.replace("_", " "),
                color = currentPriority.color
            )
        }
    }
}
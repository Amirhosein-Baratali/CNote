package com.example.cnote.feature_task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cnote.R
import com.example.cnote.core.domain.util.OrderType
import com.example.cnote.feature_task.domain.util.TaskOrder
import com.example.cnote.ui.theme.CNoteTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TasksTopBar(
    haveCompletedTask: Boolean,
    onDeleteCompletedTasks: () -> Unit,
    taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending),
    onOrderChange: (TaskOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(R.string.your_tasks)) },
        actions = {
            if (haveCompletedTask)
                Icon(
                    modifier = Modifier.clickable { onDeleteCompletedTasks() },
                    imageVector = Icons.Default.AutoDelete,
                    contentDescription = stringResource(R.string.deleted_completed_tasks)
                )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = stringResource(R.string.sort)
                )
            }
            TaskOrderSection(
                taskOrder = taskOrder,
                onOrderChange = onOrderChange,
                expanded = expanded,
                onExpandChange = { expanded = it }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TasksTopBarPreview() {
    CNoteTheme {
        Scaffold(topBar = {
            TasksTopBar(
                haveCompletedTask = true,
                onDeleteCompletedTasks = { },
                onOrderChange = {}
            )
        }) {
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .padding(it)
            )
        }
    }
}
package com.example.cnote.feature_task.presentation.tasks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cnote.R
import com.example.cnote.core.presentation.TopBar
import com.example.cnote.core.presentation.components.FloatingAddButton
import com.example.cnote.core.presentation.components.LightAndDarkPreview
import com.example.cnote.core.presentation.components.snackbar.CustomScaffold
import com.example.cnote.feature_task.presentation.tasks.components.DeleteCompletedMenuItem
import com.example.cnote.feature_task.presentation.tasks.components.TaskItem
import com.example.cnote.feature_task.presentation.util.TaskScreens
import com.example.cnote.ui.theme.CNoteTheme

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TasksScreenContent(
        navController = navController,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun TasksScreenContent(
    navController: NavController,
    state: TasksState,
    onEvent: (TasksEvent) -> Unit
) {
    var moreMenuExpanded by remember { mutableStateOf(false) }

    CustomScaffold(
        navController = navController,
        floatingActionButton = {
            FloatingAddButton(
                onClick = {
                    navController.navigate(TaskScreens.AddEditTask(null))
                }
            )
        },
        topBar = {
            TopBar(
                stringResource(R.string.tasks),
                order = state.taskOrder,
                onOrderChange = { onEvent(TasksEvent.Sort(it)) },
                showMoreIcon = state.hasCompletedTask,
                dropMenuItems = {
                    DeleteCompletedMenuItem(
                        onDeleteCompletedTasks = {
                            onEvent(TasksEvent.DeleteCompletedTasks)
                            moreMenuExpanded = false
                        }
                    )
                },
                moreExpanded = moreMenuExpanded,
                onMoreExpandedChange = { moreMenuExpanded = it },
                onSearchQueryChange = { onEvent(TasksEvent.OnSearchQueryChanged(it)) }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = {
                        navController.navigate(TaskScreens.AddEditTask(taskId = task.id))
                    },
                    onDeleteClick = { onEvent(TasksEvent.DeleteTask(task)) },
                    onCheckClick = {
                        onEvent(TasksEvent.UpdateTask(task.copy(completed = it)))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun TasksScreenPreview() {
    CNoteTheme {
        TasksScreenContent(
            navController = rememberNavController(),
            state = TasksState(),
            onEvent = {}
        )
    }
}
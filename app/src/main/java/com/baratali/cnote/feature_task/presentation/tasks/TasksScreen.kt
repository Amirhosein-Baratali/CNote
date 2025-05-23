package com.baratali.cnote.feature_task.presentation.tasks

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.TopBar
import com.baratali.cnote.core.presentation.components.FloatingAddButton
import com.baratali.cnote.core.presentation.components.LightAndDarkPreview
import com.baratali.cnote.core.presentation.components.snackbar.CustomScaffold
import com.baratali.cnote.feature_task.presentation.tasks.components.DeleteCompletedMenuItem
import com.baratali.cnote.feature_task.presentation.tasks.components.TaskItem
import com.baratali.cnote.feature_task.presentation.util.TaskScreens
import com.baratali.cnote.settings.presentation.util.SettingScreens
import com.baratali.cnote.ui.theme.CNoteTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                TasksViewModel.UIEvent.NavigateToSettings -> {
                    navController.navigate(SettingScreens.Settings)
                }
            }
        }
    }

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
    val activity = LocalActivity.current
    BackHandler {
        activity?.finish()
    }

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
                showPriorityOption = true,
                dropMenuItems = {
                    if (state.hasCompletedTask)
                        DeleteCompletedMenuItem(
                            onDeleteCompletedTasks = {
                                onEvent(TasksEvent.DeleteCompletedTasks)
                                moreMenuExpanded = false
                            }
                        )
                },
                moreExpanded = moreMenuExpanded,
                onMoreExpandedChange = { moreMenuExpanded = it },
                onSearchQueryChange = { onEvent(TasksEvent.OnSearchQueryChanged(it)) },
                settingsClicked = {
                    onEvent(TasksEvent.SettingsClicked)
                    moreMenuExpanded = false
                }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.tasksWithCategory) { tasksWithCategory ->
                TaskItem(
                    taskWithCategory = tasksWithCategory,
                    onTaskClick = {
                        navController.navigate(TaskScreens.AddEditTask(taskId = tasksWithCategory.task.id))
                    },
                    onDeleteClick = { onEvent(TasksEvent.DeleteTask(tasksWithCategory.task)) },
                    onCheckClick = {
                        onEvent(TasksEvent.UpdateTask(tasksWithCategory.task.copy(completed = it)))
                    },
                    datePickerType = state.datePickerType
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
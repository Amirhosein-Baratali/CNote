package com.example.cnote.feature_task.presentation.tasks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.TopBar
import com.example.cnote.core.presentation.components.FloatingAddButton
import com.example.cnote.feature_task.presentation.tasks.components.DeleteCompletedMenuItem
import com.example.cnote.feature_task.presentation.tasks.components.TaskItem
import com.example.cnote.feature_task.presentation.util.TaskScreens
import kotlinx.coroutines.launch

@Composable
fun TasksScreen(
    navController: NavController, viewModel: TasksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState(TasksState())
    val haveCompletedTask: Boolean = state.tasks.map { it.completed }.contains(true)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var moreMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
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
                onOrderChange = { viewModel.onEvent(TasksEvent.Sort(it)) },
                showMoreIcon = haveCompletedTask,
                dropMenuItems = {
                    DeleteCompletedMenuItem(
                        onDeleteCompletedTasks = {
                            viewModel.onEvent(TasksEvent.DeleteCompletedTasks)
                            moreMenuExpanded = false
                        }
                    )
                },
                moreExpanded = moreMenuExpanded,
                onMoreExpandedChange = { moreMenuExpanded = it },
                onSearchQueryChange = { viewModel.onEvent(TasksEvent.OnSearchQueryChanged(it)) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = {
                        navController.navigate(TaskScreens.AddEditTask(taskId = task.id))
                    },
                    onDeleteClick = {
                        viewModel.onEvent(TasksEvent.DeleteTask(task))
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Task deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(TasksEvent.RestoreTask)
                            }
                        }
                    },
                    onCheckClick = {
                        viewModel.onEvent(TasksEvent.UpdateTask(task.copy(completed = it)))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
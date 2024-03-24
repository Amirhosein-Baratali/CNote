package com.example.cnote.feature_task.presentation.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cnote.R
import com.example.cnote.core.presentation.TopBar
import com.example.cnote.core.presentation.bottom_navigation.BottomNavigation
import com.example.cnote.core.presentation.components.FloatingAddButton
import com.example.cnote.feature_task.presentation.tasks.components.TaskItem
import com.example.cnote.feature_task.presentation.util.TaskScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    navController: NavController, viewModel: TasksViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val haveCompletedTask: Boolean = state.tasks.map { it.completed }.contains(true)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingAddButton(
                onClick = { navController.navigate(TaskScreens.AddEditTask.route) },
                stringResource(R.string.add_task)
            )
        },
        topBar = {
            TopBar(
                stringResource(R.string.your_tasks),
                order = state.taskOrder,
                onOrderChange = { viewModel.onEvent(TasksEvent.Sort(it)) },
                extraActions = {
                    if (haveCompletedTask)
                        Icon(
                            modifier = Modifier.clickable { viewModel.onEvent(TasksEvent.DeleteCompletedTasks) },
                            imageVector = Icons.Default.AutoDelete,
                            contentDescription = stringResource(R.string.deleted_completed_tasks)
                        )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { BottomNavigation(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.tasks) { task ->
                TaskItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                TaskScreens.AddEditTask.route +
                                        "?${TaskScreens.ARG_Task_ID}=${task.id}"
                            )
                        },
                    task = task,
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
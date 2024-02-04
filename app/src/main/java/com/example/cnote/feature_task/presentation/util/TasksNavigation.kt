package com.example.cnote.feature_task.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cnote.feature_task.presentation.add_edit_task.AddEditTaskScreen
import com.example.cnote.feature_task.presentation.tasks.TasksScreen


fun NavGraphBuilder.tasksNavigation(navController: NavController) {
    composable(TaskScreens.Tasks.route) {
        TasksScreen(navController)
    }
    composable(route = TaskScreens.AddEditTask.route +
            "?taskId={taskId}",
        arguments = listOf(
            navArgument(TaskScreens.ARG_Task_ID) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        AddEditTaskScreen(navController = navController)
    }
}
package com.example.cnote.feature_task.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.example.cnote.feature_task.presentation.add_edit_task.AddEditTaskScreen
import com.example.cnote.feature_task.presentation.tasks.TasksScreen


fun NavGraphBuilder.tasksNavigation(navController: NavController) {
    composable(TaskScreens.Tasks.route) {
        TasksScreen(navController)
    }
    dialog(route = TaskScreens.AddEditTask.route +
            "?${TaskScreens.ARG_TASK_ID}={${TaskScreens.ARG_TASK_ID}}" +
            "&${TaskScreens.ARG_TITLE}={${TaskScreens.ARG_TITLE}}",
        arguments = listOf(
            navArgument(TaskScreens.ARG_TASK_ID) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(TaskScreens.ARG_TITLE) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        AddEditTaskScreen(navController = navController)
    }
}
package com.example.cnote.feature_task.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.cnote.feature_task.presentation.add_edit_task.AddEditTaskBottomSheet
import com.example.cnote.feature_task.presentation.tasks.TasksScreen

fun NavGraphBuilder.tasksNavigation(navController: NavController) {
    composable<TaskScreens.Tasks> {
        TasksScreen(navController)
    }
    dialog<TaskScreens.AddEditTask> {
        AddEditTaskBottomSheet(navController = navController)
    }
}
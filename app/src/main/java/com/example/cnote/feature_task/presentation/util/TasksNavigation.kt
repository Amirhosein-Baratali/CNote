package com.example.cnote.feature_task.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.example.cnote.feature_task.presentation.add_edit_task.AddEditTaskBottomSheet
import com.example.cnote.feature_task.presentation.tasks.TasksScreen

fun NavGraphBuilder.tasksNavigation(navController: NavController) {
    navigation<TaskScreens.Tasks>(startDestination = TaskScreens.TaskList) {
        composable<TaskScreens.TaskList> {
            TasksScreen(navController)
        }
        dialog<TaskScreens.AddEditTask> {
            AddEditTaskBottomSheet(navController = navController)
        }
    }
}
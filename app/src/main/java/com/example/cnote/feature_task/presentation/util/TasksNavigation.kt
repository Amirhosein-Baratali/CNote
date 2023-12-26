package com.example.cnote.feature_task.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cnote.feature_task.presentation.tasks.TasksScreen


fun NavGraphBuilder.TasksNavigation(navController: NavController) {
    composable(TaskScreens.Tasks.route) {
        TasksScreen(navController)
    }
}
package com.baratali.cnote.feature_task.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.baratali.cnote.feature_task.presentation.add_edit_task.AddEditTaskBottomSheet
import com.baratali.cnote.feature_task.presentation.categories.CategoryPickerBottomSheet
import com.baratali.cnote.feature_task.presentation.category_creation.CategoryCreationScreen
import com.baratali.cnote.feature_task.presentation.tasks.TasksScreen

fun NavGraphBuilder.tasksNavigation(navController: NavController) {
    navigation<TaskScreens.Tasks>(startDestination = TaskScreens.TaskList) {
        composable<TaskScreens.TaskList> {
            TasksScreen(navController)
        }
        dialog<TaskScreens.AddEditTask> { entry ->
            val selectedCategoryId =
                entry.savedStateHandle.get<Int>(TaskScreens.KEY_SELECTED_CATEGORY_ID)
            val refreshCount = entry.savedStateHandle[TaskScreens.KEY_REFRESH_COUNT] ?: 0
            AddEditTaskBottomSheet(
                navController = navController,
                refreshCount = refreshCount,
                selectedCategoryId = selectedCategoryId
            )
        }
        dialog<TaskScreens.Categories> {
            CategoryPickerBottomSheet(navController = navController)
        }
        dialog<TaskScreens.CategoryCreation> {
            CategoryCreationScreen(navController = navController)
        }
    }
}
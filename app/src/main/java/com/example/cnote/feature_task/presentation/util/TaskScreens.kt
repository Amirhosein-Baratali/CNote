package com.example.cnote.feature_task.presentation.util

sealed class TaskScreens(val route: String){
    object Tasks: TaskScreens("tasks_screen")
    object AddEditTask: TaskScreens("add_edit_task_screen")
}

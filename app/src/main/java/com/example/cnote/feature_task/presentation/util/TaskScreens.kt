package com.example.cnote.feature_task.presentation.util

sealed class TaskScreens(val route: String) {
    object Tasks : TaskScreens("tasks_screen")
    object AddEditTask : TaskScreens("add_edit_task_screen")

    companion object {
        const val ARG_TASK_ID = "taskId"
        const val ARG_TITLE = "title"
    }
}

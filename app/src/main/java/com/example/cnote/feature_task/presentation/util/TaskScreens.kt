package com.example.cnote.feature_task.presentation.util

import kotlinx.serialization.Serializable

sealed class TaskScreens() {
    @Serializable
    object Tasks : TaskScreens()

    @Serializable
    object TaskList : TaskScreens()

    @Serializable
    data class AddEditTask(val taskId: Int?) : TaskScreens()
}

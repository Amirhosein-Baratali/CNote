package com.example.cnote.feature_task.presentation.util

import kotlinx.serialization.Serializable

sealed class TaskScreens() {
    @Serializable
    object Tasks : TaskScreens()

    @Serializable
    data class AddEditTask(val taskId: Int?) : TaskScreens()

//    companion object {
//        const val ARG_TASK_ID = "taskId"
//        const val ARG_TITLE = "title"
//    }
}

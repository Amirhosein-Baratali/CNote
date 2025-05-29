package com.baratali.cnote.feature_task.presentation.util

import kotlinx.serialization.Serializable

sealed class TaskScreens {
    @Serializable
    object Tasks : TaskScreens()

    @Serializable
    object TaskList : TaskScreens()

    @Serializable
    data class AddEditTask(val taskId: Int?, val categoryId: Int? = null) : TaskScreens()

    @Serializable
    data class Categories(val categoryId: Int?) : TaskScreens()

    @Serializable
    object CategoryCreation : TaskScreens()

    companion object {
        const val KEY_SELECTED_CATEGORY_ID = "selectedCategoryId"
        const val KEY_REFRESH_COUNT = "refreshCount"
    }
}

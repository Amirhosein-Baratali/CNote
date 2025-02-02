package com.baratali.cnote.feature_task.presentation.categories

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory

data class CategoryPickerState(
    val categories: List<TaskCategory> = emptyList(),
    val selectedCategoryId: Int? = null,
    val isEditMode: Boolean = false
)
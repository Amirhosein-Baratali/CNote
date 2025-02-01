package com.baratali.cnote.feature_task.presentation.categories

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory

sealed class CategoryPickerEvent {
    data class CategorySelected(val category: TaskCategory) : CategoryPickerEvent()
    object AddNewCategory : CategoryPickerEvent()
    object Dismiss : CategoryPickerEvent()
}
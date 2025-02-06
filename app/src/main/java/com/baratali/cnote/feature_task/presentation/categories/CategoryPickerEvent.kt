package com.baratali.cnote.feature_task.presentation.categories

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory

sealed class CategoryPickerEvent {
    data class CategorySelected(val category: TaskCategory) : CategoryPickerEvent()
    object AddNewCategory : CategoryPickerEvent()
    object Dismiss : CategoryPickerEvent()
    object EnterEditMode : CategoryPickerEvent()
    object ExitEditMode : CategoryPickerEvent()
    data class DeleteCategory(
        val category: TaskCategory,
        val isSelected: Boolean
    ) : CategoryPickerEvent()

    object NoCategorySelected : CategoryPickerEvent()
}
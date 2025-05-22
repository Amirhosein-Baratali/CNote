package com.baratali.cnote.feature_task.presentation.add_edit_task

import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.data.data_source.model.TaskPriority
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import java.time.LocalDateTime

data class AddEditTaskState(
    val name: String = "",
    val description: String = "",
    val date: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val showDatePicker: Boolean = false,
    val selectedCategory: TaskCategory? = null,
    val currentTaskId: Int? = null,
    val datePickerType: DatePickerType = DatePickerType.JALALI
)

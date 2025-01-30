package com.baratali.cnote.feature_task.presentation.add_edit_task

import com.baratali.cnote.feature_task.domain.model.TaskPriority
import java.time.LocalDateTime

data class AddEditTaskState(
    val name: String = "",
    val description: String = "",
    val date: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val showDatePicker:Boolean = false
)

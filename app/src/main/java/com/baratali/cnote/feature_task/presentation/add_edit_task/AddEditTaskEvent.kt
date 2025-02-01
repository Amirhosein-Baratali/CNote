package com.baratali.cnote.feature_task.presentation.add_edit_task

import com.baratali.cnote.feature_task.data.data_source.model.TaskPriority
import java.time.LocalDateTime

sealed class AddEditTaskEvent {
    data class NameChanged(val name: String) : AddEditTaskEvent()
    data class DescriptionChanged(val description: String) : AddEditTaskEvent()
    object SaveTask : AddEditTaskEvent()
    object Dismiss : AddEditTaskEvent()
    object DateClicked : AddEditTaskEvent()
    data class DateSelected(val date: LocalDateTime?) : AddEditTaskEvent()
    object DateDismissed : AddEditTaskEvent()
    data class PrioritySelected(val priority: TaskPriority) : AddEditTaskEvent()
    object CategoryClicked : AddEditTaskEvent()
}
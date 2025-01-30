package com.example.cnote.feature_task.presentation.add_edit_task

import java.time.LocalDateTime

data class AddEditTaskState(
    val name: String = "",
    val description: String = "",
    val date: LocalDateTime? = null,
    val showDatePicker:Boolean = false
)

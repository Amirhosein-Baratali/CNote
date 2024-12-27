package com.example.cnote.feature_task.presentation.add_edit_task

data class AddEditTaskState(
    val name: String = "",
    val description: String = "",
    val date: String? = null,
    val showDatePicker:Boolean = false
)

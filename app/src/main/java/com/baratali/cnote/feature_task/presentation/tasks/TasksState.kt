package com.baratali.cnote.feature_task.presentation.tasks

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.domain.util.OrderType
import com.baratali.cnote.feature_task.data.data_source.model.TaskWithCategory
import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType

data class TasksState(
    val tasksWithCategory: List<TaskWithCategory> = emptyList(),
    val taskOrder: Order = Order.Date(OrderType.Descending),
    val datePickerType: DatePickerType = DatePickerType.GEORGIAN
) {
    val hasCompletedTask = tasksWithCategory.map { it.task }.map { it.completed }.contains(true)
}
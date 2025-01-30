package com.baratali.cnote.feature_task.domain.use_case

import com.baratali.cnote.feature_task.domain.model.InvalidTaskException
import com.baratali.cnote.feature_task.domain.model.Task
import com.baratali.cnote.feature_task.domain.repository.TaskRepository
import com.baratali.cnote.feature_task.domain.use_case.notification.ScheduleTaskNotificationUseCase

class AddTask(
    private val repository: TaskRepository,
    private val scheduleTaskNotificationUseCase: ScheduleTaskNotificationUseCase
) {
    suspend operator fun invoke(task: Task) {
        if (task.name.isBlank()) {
            throw InvalidTaskException("Task Name Cannot Be Empty")
        }
        repository.insertTask(task)
        scheduleTaskNotificationUseCase(task)
    }
}
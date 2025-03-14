package com.baratali.cnote.feature_task.domain.use_case.notification

import com.baratali.cnote.R
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.domain.model.TaskNotification
import javax.inject.Inject

class CalculateTaskNotificationsUseCase @Inject constructor() {
    operator fun invoke(task: Task): List<TaskNotification> =
        task.date?.let { date ->
            mutableListOf(
                TaskNotification(
                    time = date,
                    titleId = R.string.time_to_do_it,
                    description = task.name,
                    notificationId = task.id ?: 0
                )
            )/*.apply {
                if (date.minusHours(1).isAfter(LocalDateTime.now())) {
                    add(
                        TaskNotification(
                            time = date.minusHours(1),
                            titleId = R.string.only_1_hour_left,
                            description = task.name,
                            notificationId = task.id ?: 0
                        )
                    )
                }
            }*/
        } ?: emptyList()
}
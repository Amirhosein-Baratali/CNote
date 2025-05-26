package com.baratali.cnote.feature_task.presentation.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.domain.use_case.notification.CalculateTaskNotificationsUseCase
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val calculateNotificationTimesUseCase: CalculateTaskNotificationsUseCase,
    private val notificationHelper: NotificationHelper,
    private val gson: Gson
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_TASK = "task_json"
    }

    override suspend fun doWork(): Result {
        val taskJson = inputData.getString(KEY_TASK)
        val task = gson.fromJson(taskJson, Task::class.java)
        val taskNotifications = calculateNotificationTimesUseCase(task)

        taskNotifications.forEach { taskNotification ->
            val delay = Duration.between(LocalDateTime.now(), taskNotification.time)
            val notificationId = taskNotification.time.toString().hashCode()
            delay.toMillis().takeIf { it > 0 }?.let {
                delay(it)
                notificationHelper.showNotification(
                    title = context.getString(taskNotification.titleId),
                    message = taskNotification.description,
                    notificationId = notificationId
                )
            }
        }
        return Result.success()
    }
}
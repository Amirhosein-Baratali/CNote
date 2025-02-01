package com.baratali.cnote.feature_task.domain.use_case.notification

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.presentation.notification.NotificationWorker
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ScheduleTaskNotificationUseCase @Inject constructor(
    private val workManager: WorkManager,
    private val dataStoreRepository: DataStoreRepository,
    private val gson: Gson
) {
    suspend operator fun invoke(task: Task) {
        if (dataStoreRepository.getSettings().first().notificationEnabled) {
            val taskJson = gson.toJson(task)
            val inputData =
                workDataOf(NotificationWorker.KEY_TASK to taskJson)
            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(inputData)
                .build()
            workManager.enqueueUniqueWork(
                "NotificationWorkerForCnote",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}
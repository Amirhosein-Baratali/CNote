package com.example.cnote.feature_task.domain.model

import androidx.annotation.StringRes
import java.time.LocalDateTime

data class TaskNotification(
    val time: LocalDateTime,
    @StringRes val titleId: Int,
    val description: String,
    val notificationId: Int
)

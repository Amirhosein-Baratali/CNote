package com.baratali.cnote

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.util.Log
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.baratali.cnote.feature_task.presentation.notification.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CNoteApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val sound = "android.resource://${packageName}/${R.raw.notif_sound}".toUri()
        val audioAttributes = AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        val channel = NotificationChannel(
            NotificationHelper.CHANNEL_ID,
            getString(R.string.task_reminder),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(sound, audioAttributes)
            vibrationPattern = longArrayOf(500, 500, 500, 500, 500)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

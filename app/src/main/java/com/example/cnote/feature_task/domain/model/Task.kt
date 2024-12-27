package com.example.cnote.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val description: String,
    val completed: Boolean,
    val timeCreated: Long = System.currentTimeMillis(),
    val importance: Boolean = false,
    val date: String? = null
) {
//    val timeStampFormatted: String
//        get() = DateFormat.getDateTimeInstance().format(timeStamp)
}

class InvalidTaskException(message: String) : Exception(message)
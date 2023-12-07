package com.example.cnote.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity
data class Task(
    val name:String,
    val description:String,
    val completed:Boolean,
    val timeStamp:Long = System.currentTimeMillis(),
    val importance: Boolean = false,
    @PrimaryKey val id: Int? = null
) {
    val timeStampFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timeStamp)
}

class InvalidTaskException(message:String) : Exception(message)
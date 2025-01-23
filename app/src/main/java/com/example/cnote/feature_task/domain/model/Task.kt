package com.example.cnote.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cnote.feature_task.data.util.formatToDisplay
import java.time.LocalDateTime

@Entity
data class Task(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val description: String,
    val completed: Boolean,
    val timeCreated: Long = System.currentTimeMillis(),
    val importance: Boolean = false,
    val date: LocalDateTime? = null
){
    val formattedDate: String?
        get() = date?.formatToDisplay()
}
class InvalidTaskException(message: String) : Exception(message)
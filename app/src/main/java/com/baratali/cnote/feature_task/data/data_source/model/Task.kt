package com.baratali.cnote.feature_task.data.data_source.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.baratali.cnote.feature_task.data.util.formatToDisplay
import java.time.LocalDateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = TaskCategory::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val description: String,
    val completed: Boolean,
    val timeCreated: Long = System.currentTimeMillis(),
    val date: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val categoryId: Int? = null
) {
    val formattedDate: String?
        get() = date?.formatToDisplay()
}

class InvalidTaskException(message: String) : Exception(message)
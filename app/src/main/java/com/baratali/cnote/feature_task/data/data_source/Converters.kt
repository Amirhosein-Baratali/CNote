package com.baratali.cnote.feature_task.data.data_source

import androidx.room.TypeConverter
import com.baratali.cnote.feature_task.data.util.toEpochMillis
import com.baratali.cnote.feature_task.data.util.toLocalDateTime
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.toEpochMillis()
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.toLocalDateTime()
    }
}
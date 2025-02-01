package com.baratali.cnote.feature_task.data.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.toEpochMillis(): Long =
    this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime.formatToDisplay(): String {
    val formatter = DateTimeFormatter.ofPattern("yy/MM/dd, HH:mm")
    return this.format(formatter)
}
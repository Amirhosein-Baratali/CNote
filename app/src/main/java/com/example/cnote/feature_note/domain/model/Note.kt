package com.example.cnote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cnote.ui.theme.BabyBlue
import com.example.cnote.ui.theme.LightGreen
import com.example.cnote.ui.theme.RedOrange
import com.example.cnote.ui.theme.RedPink
import com.example.cnote.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
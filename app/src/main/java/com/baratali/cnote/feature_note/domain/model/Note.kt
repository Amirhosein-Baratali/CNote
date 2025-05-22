package com.baratali.cnote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baratali.cnote.ui.theme.BabyBlue
import com.baratali.cnote.ui.theme.LightGreen
import com.baratali.cnote.ui.theme.RedOrange
import com.baratali.cnote.ui.theme.RedPink
import com.baratali.cnote.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val locked: Boolean = false,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

    fun matchWithSearchQuery(query: String): Boolean {
        return title.contains(query, ignoreCase = true) || content.contains(
            query,
            ignoreCase = true
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)
package com.example.cnote.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import com.example.cnote.feature_note.domain.model.Note

data class NoteState(
    val title: String = "",
    val content: String = "",
    val color: Int = Note.noteColors.random().toArgb()
) {
    companion object {
        fun fromNote(note: Note) = NoteState(
            title = note.title,
            content = note.content,
            color = note.color
        )
    }
}

fun NoteState.toNote(id: Int? = null) = Note(
    title = title,
    content = content,
    timestamp = System.currentTimeMillis(),
    color = color,
    id = id
)
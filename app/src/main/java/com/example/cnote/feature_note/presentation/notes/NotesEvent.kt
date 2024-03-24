package com.example.cnote.feature_note.presentation.notes

import com.example.cnote.core.domain.util.Order
import com.example.cnote.feature_note.domain.model.Note


sealed class NotesEvent {
    data class Sort(val noteOrder: Order) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
}

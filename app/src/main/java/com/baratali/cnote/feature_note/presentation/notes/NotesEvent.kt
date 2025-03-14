package com.baratali.cnote.feature_note.presentation.notes

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_note.domain.model.Note


sealed class NotesEvent {
    data class Sort(val noteOrder: Order) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    data class LockNote(val note: Note) : NotesEvent()
    data class OnSearchQueryChanged(val query: String) : NotesEvent()
    data class OnNoteClicked(val note: Note) : NotesEvent()
    object AddButtonCLicked : NotesEvent()
    object SettingsClicked : NotesEvent()
}

package com.baratali.cnote.feature_note.presentation.add_edit_note

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
    object ExitDialogDismissed : AddEditNoteEvent()
    object BackButtonClicked : AddEditNoteEvent()
    object OnStop : AddEditNoteEvent()
    object DiscardChanges : AddEditNoteEvent()
}
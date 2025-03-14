package com.baratali.cnote.feature_note.presentation.util

import kotlinx.serialization.Serializable

sealed class NoteScreens {
    @Serializable
    object Notes : NoteScreens()

    @Serializable
    object NoteList : NoteScreens()

    @Serializable
    data class AddEditNote(val noteId: Int?) : NoteScreens()
}

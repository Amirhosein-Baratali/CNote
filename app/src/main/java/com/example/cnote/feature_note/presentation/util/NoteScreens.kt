package com.example.cnote.feature_note.presentation.util

import kotlinx.serialization.Serializable

sealed class NoteScreens {
    @Serializable
    object Notes : NoteScreens()

    @Serializable
    data class AddEditNote(val noteId: Int?, val noteColor: Int?) : NoteScreens()
}

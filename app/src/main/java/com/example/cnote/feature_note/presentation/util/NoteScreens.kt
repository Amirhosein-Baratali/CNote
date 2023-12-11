package com.example.cnote.feature_note.presentation.util

sealed class NoteScreens(val route: String) {
    object Notes: NoteScreens("notes_screen")
    object AddEditNote: NoteScreens("add_edit_note_screen")
}

package com.baratali.cnote.feature_note.presentation.util

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.baratali.cnote.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.baratali.cnote.feature_note.presentation.notes.NotesScreen

fun NavGraphBuilder.notesNavigation(navController: NavController) {
    navigation<NoteScreens.Notes>(startDestination = NoteScreens.NoteList) {
        composable<NoteScreens.NoteList> {
            NotesScreen(navController = navController)
        }
        composable<NoteScreens.AddEditNote> {
            val color = it.toRoute<NoteScreens.AddEditNote>().noteColor
            AddEditNoteScreen(
                navController = navController,
                noteColor = color
            )
        }
    }
}


package com.example.cnote.feature_note.domain.use_case

import com.example.cnote.feature_note.domain.model.InvalidNoteException
import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        val content = note.content
        if (content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }

        val updatedNote = note.copy(
            title = note.title.takeIf { it.isNotBlank() } ?: content.take(10)
        )

        repository.insertNote(updatedNote)
    }
}
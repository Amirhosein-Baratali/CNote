package com.baratali.cnote.feature_note.domain.use_case

import com.baratali.cnote.feature_note.domain.model.InvalidNoteException
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        with(note) {
            if (title.isBlank() && content.isBlank()) {
                throw InvalidNoteException("Oops! It's empty. Fill me up?")
            }
            val updatedNote = copy(
                title = title.ifBlank { content.take(10) },
                content = content.ifBlank { title }
            )
            repository.insertNote(updatedNote)
        }
    }
}
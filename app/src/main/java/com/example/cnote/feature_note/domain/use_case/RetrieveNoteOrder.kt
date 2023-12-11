package com.example.cnote.feature_note.domain.use_case

import com.example.cnote.feature_note.domain.repository.NoteRepository
import com.example.cnote.feature_note.domain.util.NoteOrder

class RetrieveNoteOrder(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(): NoteOrder {
        return repository.getSavedNoteOrder()
    }
}
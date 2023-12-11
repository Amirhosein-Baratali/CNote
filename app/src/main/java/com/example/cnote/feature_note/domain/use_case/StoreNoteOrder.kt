package com.example.cnote.feature_note.domain.use_case

import com.example.cnote.feature_note.domain.repository.NoteRepository
import com.example.cnote.feature_note.domain.util.NoteOrder

class StoreNoteOrder(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteOrder: NoteOrder) {
        repository.saveNoteOrder(noteOrder)
    }
}
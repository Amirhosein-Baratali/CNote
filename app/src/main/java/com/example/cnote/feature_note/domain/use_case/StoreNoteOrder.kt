package com.example.cnote.feature_note.domain.use_case

import com.example.cnote.core.domain.util.Order
import com.example.cnote.feature_note.domain.repository.NoteRepository

class StoreNoteOrder(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteOrder: Order) {
        repository.saveOrder(noteOrder)
    }
}
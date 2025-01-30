package com.baratali.cnote.feature_note.domain.use_case

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_note.domain.repository.NoteRepository

class StoreNoteOrder(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteOrder: Order) {
        repository.saveOrder(noteOrder)
    }
}
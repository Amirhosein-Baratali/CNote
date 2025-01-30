package com.baratali.cnote.feature_note.domain.use_case

import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_note.domain.repository.NoteRepository

class RetrieveNoteOrder(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(): Order {
        return repository.getSavedOrder()
    }
}
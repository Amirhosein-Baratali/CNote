package com.baratali.cnote.feature_note.domain.use_case

import com.baratali.cnote.feature_note.domain.repository.NoteRepository

class SetLockedNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int, isLocked: Boolean) {
        repository.setNoteLocked(id, isLocked)
    }
}
package com.baratali.cnote.feature_note.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()
    private var noteOrder by mutableStateOf(Order.defaultDateOrder())

    override suspend fun saveOrder(order: Order) {
        noteOrder = order
    }

    override suspend fun getSavedOrder(): Order {
        return noteOrder
    }

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}
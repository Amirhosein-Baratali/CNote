package com.baratali.cnote.feature_note.domain.repository

import com.baratali.cnote.core.domain.repository.OrderRepository
import com.baratali.cnote.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository : OrderRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note): Long

    suspend fun deleteNote(note: Note)

    suspend fun setNoteLocked(noteId: Int, isLocked: Boolean)
}
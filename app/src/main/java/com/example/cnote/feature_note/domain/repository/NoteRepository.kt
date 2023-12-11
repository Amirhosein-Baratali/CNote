package com.example.cnote.feature_note.domain.repository

import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun saveNoteOrder(noteOrder: NoteOrder)

    suspend fun getSavedNoteOrder(): NoteOrder
}
package com.baratali.cnote.feature_note.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.util.getStringValue
import com.baratali.cnote.feature_note.data.data_source.NoteDao
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val dataStorePreferences: DataStore<Preferences>
) : NoteRepository {

    private companion object {
        val KEY_NOTE_ORDER = stringPreferencesKey(name = "note_order")
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun saveOrder(order: Order) {
        val noteOrderName = order.toStringValue()
        dataStorePreferences.edit { preferences ->
            preferences[KEY_NOTE_ORDER] = noteOrderName
        }
    }

    override suspend fun setNoteLocked(noteId: Int, isLocked: Boolean) {
        dao.setNoteLocked(noteId, isLocked)
    }

    override suspend fun getSavedOrder(): Order {
        val noteOrderName: String? = dataStorePreferences.getStringValue(KEY_NOTE_ORDER)
        return Order.fromString(noteOrderName)
    }
}
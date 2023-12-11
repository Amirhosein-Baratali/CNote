package com.example.cnote.feature_note.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cnote.core.util.getStringValue
import com.example.cnote.feature_note.data.data_source.NoteDao
import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.repository.NoteRepository
import com.example.cnote.feature_note.domain.util.NoteOrder
import com.example.cnote.feature_note.domain.util.NoteOrderDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val dataStorePreferences: DataStore<Preferences>
) : NoteRepository {

    private companion object {
        val KEY_NOTE_ORDER = stringPreferencesKey(name = "note_order")
    }

    private var gson = GsonBuilder()
        .registerTypeAdapter(NoteOrder::class.java, NoteOrderDeserializer())
        .create()

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

    override suspend fun saveNoteOrder(noteOrder: NoteOrder) {
        val noteOrderName = noteOrder.getName(noteOrder.orderType)
        dataStorePreferences.edit { preferences ->
            preferences[KEY_NOTE_ORDER] = noteOrderName
        }
    }

    override suspend fun getSavedNoteOrder(): NoteOrder {
        val noteOrderName:String? = dataStorePreferences.getStringValue(KEY_NOTE_ORDER)
        return NoteOrder.fromName(noteOrderName)
    }
}
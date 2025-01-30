package com.baratali.cnote.feature_note.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.baratali.cnote.feature_note.data.data_source.NoteDatabase
import com.baratali.cnote.feature_note.data.repository.NoteRepositoryImpl
import com.baratali.cnote.feature_note.domain.repository.NoteRepository
import com.baratali.cnote.feature_note.domain.use_case.AddNote
import com.baratali.cnote.feature_note.domain.use_case.DeleteNote
import com.baratali.cnote.feature_note.domain.use_case.GetNote
import com.baratali.cnote.feature_note.domain.use_case.GetNotes
import com.baratali.cnote.feature_note.domain.use_case.NoteUseCases
import com.baratali.cnote.feature_note.domain.use_case.RetrieveNoteOrder
import com.baratali.cnote.feature_note.domain.use_case.StoreNoteOrder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestNotesModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        db: NoteDatabase,
        dataStore: DataStore<Preferences>,
    ): NoteRepository {
        return NoteRepositoryImpl(db.noteDao, dataStore)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
            storeNoteOrder = StoreNoteOrder(repository),
            retrieveNoteOrder = RetrieveNoteOrder(repository)
        )
    }
}
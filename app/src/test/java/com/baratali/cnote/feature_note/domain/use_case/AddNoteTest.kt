package com.baratali.cnote.feature_note.domain.use_case

import com.baratali.cnote.feature_note.data.repository.FakeNoteRepository
import com.baratali.cnote.feature_note.domain.model.InvalidNoteException
import com.baratali.cnote.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeRepository)
        addNote = AddNote(fakeRepository)
    }

    @Test
    fun `Empty name, throws invalid note exception`() = runBlocking {
        val note = Note("", "content", 0, 12)

        try {
            addNote(note)
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(InvalidNoteException::class.java)
        }
    }

    @Test
    fun `Empty content, throws invalid note exception`() = runBlocking {
        val note = Note("title", "", 0, 12)

        try {
            addNote(note)
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(InvalidNoteException::class.java)
        }
    }

    @Test
    fun `Add note to repository, works fine`() = runBlocking() {
        val note = Note("title", "content", 0, 12)
        addNote(note)
        val notes = getNotes().first()

        assertThat(note).isIn(notes)
    }
}
package com.example.cnote.feature_note.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cnote.core.domain.util.Order
import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _searchText = MutableStateFlow("")

    private val _state = MutableStateFlow(NotesState())
    val state = _searchText
        .combine(_state) { text, state ->
            state.copy(
                notes = state.notes.filter {
                    it.title.contains(text) || it.content.contains(text)
                }
            )
        }

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        viewModelScope.launch {
            getNotes(noteUseCases.retrieveNoteOrder())
        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Sort -> {
                val noteOrder = event.noteOrder
                getNotes(noteOrder)
                viewModelScope.launch {
                    noteUseCases.storeNoteOrder(noteOrder)
                }
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    val note = event.note
                    noteUseCases.deleteNote(note)
                    recentlyDeletedNote = note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.OnSearchQueryChanged -> _searchText.update { event.query }
        }
    }

    private fun getNotes(noteOrder: Order) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = NotesState(notes, noteOrder)
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        getNotesJob?.cancel()
        super.onCleared()
    }
}
package com.example.cnote.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cnote.feature_note.domain.model.InvalidNoteException
import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.use_case.NoteUseCases
import com.example.cnote.feature_note.presentation.util.NoteScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var saveNoteJob: Job? = null
    private var getNoteJob: Job? = null

    private var initialNote: Note? = null
    private var currentNoteId: Int? = null

    private val _noteState = mutableStateOf(NoteState())
    val noteState: State<NoteState> = _noteState

    private fun hasNoteContent(): Boolean {
        return noteState.value.title.isNotBlank() || noteState.value.content.isNotBlank()
    }

    private fun hasNoteChanged(): Boolean = initialNote?.run {
        noteState.value.title.trim() != title.trim()
                || noteState.value.content.trim() != content.trim()
                || noteState.value.color != color
    } ?: false

    fun isNoteEdited(): Boolean = initialNote?.let { hasNoteChanged() } ?: hasNoteContent()

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        savedStateHandle.toRoute<NoteScreens.AddEditNote>().noteId?.let { noteId ->
            getInitialNote(noteId)
        }
    }

    private fun getInitialNote(noteId: Int) {
        getNoteJob = viewModelScope.launch {
            noteUseCases.getNote(noteId)?.also { note ->
                currentNoteId = note.id
                _noteState.value = NoteState.fromNote(note)
                initialNote = note
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        _noteState.value = when (event) {
            is AddEditNoteEvent.EnteredTitle -> noteState.value.copy(title = event.value)
            is AddEditNoteEvent.EnteredContent -> noteState.value.copy(content = event.value)
            is AddEditNoteEvent.ChangeColor -> noteState.value.copy(color = event.color)
            is AddEditNoteEvent.SaveNote -> {
                saveNote()
                return
            }
        }
    }

    private fun saveNote() {
        saveNoteJob = viewModelScope.launch {
            try {
                noteUseCases.addNote(noteState.value.toNote(currentNoteId))
                _eventFlow.send(UiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.send(
                    UiEvent.ShowError(
                        message = e.message ?: "Couldn't save note"
                    )
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowError(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }

    override fun onCleared() {
        getNoteJob?.cancel()
        saveNoteJob?.cancel()
        super.onCleared()
    }
}
package com.example.cnote.feature_note.presentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cnote.R
import com.example.cnote.core.presentation.BaseViewModel
import com.example.cnote.core.presentation.components.UiText
import com.example.cnote.core.presentation.components.snackbar.SnackbarAction
import com.example.cnote.core.presentation.components.snackbar.SnackbarType
import com.example.cnote.feature_note.domain.model.InvalidNoteException
import com.example.cnote.feature_note.domain.model.Note
import com.example.cnote.feature_note.domain.use_case.NoteUseCases
import com.example.cnote.feature_note.presentation.util.NoteScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var saveNoteJob: Job? = null
    private var getNoteJob: Job? = null

    private var initialNote: Note? = null
    private var currentNoteId: Int? = null

    private val _noteState = MutableStateFlow(NoteState())
    val noteState = _noteState.asStateFlow()

    private fun hasNoteContent(): Boolean {
        return noteState.value.title.isNotBlank() || noteState.value.content.isNotBlank()
    }

    private fun hasNoteChanged(): Boolean = initialNote?.run {
        noteState.value.title.trim() != title.trim()
                || noteState.value.content.trim() != content.trim()
                || noteState.value.color != color
    } ?: false

    private fun isNoteEdited(): Boolean = initialNote?.let { hasNoteChanged() } ?: hasNoteContent()

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
        _noteState.update {
            when (event) {
                is AddEditNoteEvent.EnteredTitle -> it.copy(title = event.value)
                is AddEditNoteEvent.EnteredContent -> it.copy(content = event.value)
                is AddEditNoteEvent.ChangeColor -> it.copy(color = event.color)
                is AddEditNoteEvent.SaveNote -> {
                    saveNote()
                    it
                }

                AddEditNoteEvent.ExitDialogDismissed -> it.copy(showExitDialog = false)
                AddEditNoteEvent.BackButtonClicked -> {
                    if (isNoteEdited())
                        it.copy(showExitDialog = true)
                    else {
                        viewModelScope.launch { _eventFlow.send(UiEvent.NavigateUp) }
                        it
                    }
                }
            }
        }
    }

    private fun saveNote() {
        saveNoteJob?.cancel()
        saveNoteJob = viewModelScope.launch {
            try {
                noteUseCases.addNote(noteState.value.toNote(currentNoteId))
                _eventFlow.send(UiEvent.NavigateUp)
            } catch (e: InvalidNoteException) {
                e.message?.let {
                    showSnackbar(
                        it,
                        action = SnackbarAction(
                            name = UiText.StringResource(R.string.not_now),
                            action = { _eventFlow.send(UiEvent.NavigateUp) }
                        )
                    )
                } ?: showSnackbar(
                    messageId = R.string.cant_save_note,
                    snackbarType = SnackbarType.ERROR
                )
            }
        }
    }

    sealed class UiEvent {
        object NavigateUp : UiEvent()
    }

    override fun onCleared() {
        getNoteJob?.cancel()
        saveNoteJob?.cancel()
        super.onCleared()
    }
}
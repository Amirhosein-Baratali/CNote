package com.baratali.cnote.feature_note.presentation.notes

import androidx.lifecycle.viewModelScope
import com.baratali.cnote.R
import com.baratali.cnote.core.domain.util.Order
import com.baratali.cnote.core.presentation.BaseViewModel
import com.baratali.cnote.core.presentation.components.UiText
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarAction
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.domain.use_case.NoteUseCases
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import com.baratali.cnote.settings.presentation.util.PasswordMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel() {

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _searchText = MutableStateFlow("")

    private val _state = MutableStateFlow(NotesState())
    val state = _searchText
        .combine(_state) { text, state ->
            state.copy(
                notes = state.notes.filter { it.matchWithSearchQuery(text) }
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            NotesState()
        )

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
                    showUndoSnackbar {
                        noteUseCases.addNote(note)
                    }
                }
            }

            is NotesEvent.OnNoteClicked -> {
                viewModelScope.launch {
                    event.note.run {
                        if (locked) {
                            _eventFlow.send(UIEvent.NavigateToPassword(id, PasswordMode.OPEN_NOTE))
                        } else {
                            _eventFlow.send(UIEvent.NavigateToEditNote(this))
                        }
                    }
                }
            }

            is NotesEvent.OnSearchQueryChanged -> _searchText.update { event.query }
            NotesEvent.AddButtonCLicked -> {
                viewModelScope.launch {
                    _eventFlow.send(UIEvent.NavigateToAddNote)
                }
            }

            NotesEvent.SettingsClicked -> viewModelScope.launch {
                _eventFlow.send(UIEvent.NavigateToSettings)
            }

            is NotesEvent.LockNote -> viewModelScope.launch {
                val note = event.note
                note.id?.let { noteId ->
                    if (note.locked) {
                        _eventFlow.send(
                            UIEvent.NavigateToPassword(
                                noteId,
                                PasswordMode.UNLOCK_NOTE
                            )
                        )
                    } else {
                        if (dataStoreRepository.getPasswordHash().isNullOrEmpty()) {
                            _eventFlow.send(
                                UIEvent.NavigateToPassword(
                                    noteId,
                                    PasswordMode.LOCK_NOTE_WITH_NEW_PASSWORD
                                )
                            )
                        } else {
                            noteUseCases.setLockedNote(noteId, true)
                        }
                    }

                }
            }
        }
    }

    private fun showUndoSnackbar(onUndo: suspend () -> Unit = {}) {
        showSnackbar(
            messageId = R.string.note_deleted,
            action = SnackbarAction(
                name = UiText.StringResource(R.string.undo),
                action = onUndo
            )
        )
    }

    private fun getNotes(noteOrder: Order) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = NotesState(notes, noteOrder)
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        object NavigateToAddNote : UIEvent()
        data class NavigateToEditNote(val note: Note) : UIEvent()
        object NavigateToSettings : UIEvent()
        data class NavigateToPassword(
            val noteId: Int?,
            val mode: PasswordMode
        ) : UIEvent()
    }

    override fun onCleared() {
        getNotesJob?.cancel()
        super.onCleared()
    }
}
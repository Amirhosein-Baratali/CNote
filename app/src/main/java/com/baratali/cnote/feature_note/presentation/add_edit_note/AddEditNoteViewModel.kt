package com.baratali.cnote.feature_note.presentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.baratali.cnote.R
import com.baratali.cnote.core.presentation.BaseViewModel
import com.baratali.cnote.core.presentation.components.UiText
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarAction
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarType
import com.baratali.cnote.feature_note.domain.model.InvalidNoteException
import com.baratali.cnote.feature_note.domain.model.Note
import com.baratali.cnote.feature_note.domain.use_case.NoteUseCases
import com.baratali.cnote.feature_note.presentation.SaveNoteWorker
import com.baratali.cnote.feature_note.presentation.util.NoteScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val workManager: WorkManager,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var saveNoteJob: Job? = null
    private var getNoteJob: Job? = null
    private var initialNote: Note? = null
    private var currentNoteId: Int? = null
    private var workName: String? = null

    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        savedStateHandle.toRoute<NoteScreens.AddEditNote>().noteId?.let { noteId ->
            currentNoteId = noteId
            workName = "save_note_$noteId"
            fetchNote(noteId)
        } ?: run {
            workName = "save_note_new_${UUID.randomUUID()}"
        }
        observeWorkStatus()
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> _state.update { it.copy(title = event.value) }
            is AddEditNoteEvent.EnteredContent -> _state.update { it.copy(content = event.value) }
            is AddEditNoteEvent.ChangeColor -> _state.update { it.copy(color = event.color) }
            is AddEditNoteEvent.SaveNote -> saveNote()
            is AddEditNoteEvent.ExitDialogDismissed -> _state.update { it.copy(showExitDialog = false) }
            is AddEditNoteEvent.BackButtonClicked -> handleBackButton()
            is AddEditNoteEvent.OnStop -> scheduleSaveNoteWorkIfEdited()
            is AddEditNoteEvent.DiscardChanges -> discardChanges()
        }
    }

    private fun fetchNote(noteId: Int) {
        getNoteJob = viewModelScope.launch {
            noteUseCases.getNote(noteId)?.let { note ->
                currentNoteId = note.id
                _state.update { NoteState.fromNote(note) }
                initialNote = note
            }
        }
    }

    private fun hasNoteContent(): Boolean =
        state.value.title.isNotBlank() || state.value.content.isNotBlank()

    private fun hasNoteChanged(): Boolean = initialNote?.let {
        state.value.title.trim() != it.title.trim() ||
                state.value.content.trim() != it.content.trim() ||
                state.value.color != it.color
    } == true

    fun isNoteEdited(): Boolean = initialNote?.let { hasNoteChanged() } ?: hasNoteContent()

    private fun handleBackButton() {
        if (isNoteEdited()) {
            _state.update { it.copy(showExitDialog = true) }
        } else {
            viewModelScope.launch { _eventFlow.send(UiEvent.NavigateUp) }
        }
    }

    private fun saveNote() {
        saveNoteJob?.cancel()
        saveNoteJob = viewModelScope.launch {
            try {
                currentNoteId = noteUseCases.addNote(state.value.toNote(currentNoteId)).toInt()
                initialNote = state.value.toNote(currentNoteId)
                _eventFlow.send(UiEvent.NavigateUp)
            } catch (e: InvalidNoteException) {
                e.message?.let {
                    showSnackbar(
                        message = it,
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

    private fun scheduleSaveNoteWorkIfEdited() {
        if (isNoteEdited()) {
            scheduleSaveNoteWork()
        }
    }

    private fun scheduleSaveNoteWork() {
        val note = state.value.toNote(currentNoteId)
        val inputData = Data.Builder()
            .putInt(SaveNoteWorker.KEY_ID, currentNoteId ?: -1)
            .putString(SaveNoteWorker.KEY_TITLE, note.title)
            .putString(SaveNoteWorker.KEY_CONTENT, note.content)
            .putInt(SaveNoteWorker.KEY_COLOR, note.color)
            .putLong(SaveNoteWorker.KEY_TIMESTAMP, note.timestamp)
            .build()
        val saveNoteWork = OneTimeWorkRequestBuilder<SaveNoteWorker>()
            .setInputData(inputData)
            .build()

        workName?.let { name ->
            workManager.enqueueUniqueWork(name, ExistingWorkPolicy.REPLACE, saveNoteWork)
        }
    }

    private fun observeWorkStatus() {
        workName?.let { name ->
            viewModelScope.launch {
                workManager.getWorkInfosForUniqueWorkFlow(name).collect { workInfos ->
                    workInfos.forEach { workInfo ->
                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            val savedNoteId = workInfo.outputData.getLong(
                                SaveNoteWorker.KEY_SAVED_NOTE_ID, -1L
                            )
                            if (savedNoteId != -1L) {
                                currentNoteId = savedNoteId.toInt()
                                if (initialNote == null) {
                                    initialNote = state.value.toNote(currentNoteId)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun discardChanges() {
        initialNote = state.value.toNote(currentNoteId)
        _state.update { it.copy(showExitDialog = false) }
        viewModelScope.launch { _eventFlow.send(UiEvent.NavigateUp) }
    }

    override fun onCleared() {
        getNoteJob?.cancel()
        saveNoteJob?.cancel()
        super.onCleared()
    }

    sealed class UiEvent {
        object NavigateUp : UiEvent()
    }
}
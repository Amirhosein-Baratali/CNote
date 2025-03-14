package com.baratali.cnote.settings.presentation.password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.baratali.cnote.core.presentation.BaseViewModel
import com.baratali.cnote.feature_note.domain.use_case.NoteUseCases
import com.baratali.cnote.settings.domain.use_case.UpdatePasswordUseCase
import com.baratali.cnote.settings.domain.use_case.VerifyPasswordUseCase
import com.baratali.cnote.settings.presentation.util.SettingScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val verifyPasswordUseCase: VerifyPasswordUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases
) : BaseViewModel() {

    private val _state = MutableStateFlow(PasswordState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    init {
        _state.update { it.copy(mode = savedStateHandle.toRoute<SettingScreens.Password>().mode) }
    }

    fun onEvent(event: PasswordEvent) {
        when (event) {
            is PasswordEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password, passwordHasError = false) }
            }

            is PasswordEvent.RepeatPasswordChanged -> {
                _state.update { it.copy(repeatPassword = event.repeatPassword) }
            }

            PasswordEvent.PasswordConfirmed -> {
                viewModelScope.launch {
                    handlePasswordConfirmation()
                }
            }

            PasswordEvent.Dismiss -> {
                viewModelScope.launch {
                    _eventFlow.send(UIEvent.NavigateBack)
                }
            }
        }
    }

    private suspend fun handlePasswordConfirmation() {

        when (state.value.mode) {
            PasswordMode.SET_PASSWORD -> {
                updatePassword()
            }

            PasswordMode.CHANGE_PASSWORD -> {
                when (state.value.changePasswordStep) {
                    ChangePasswordStep.EnterCurrent -> verifyPassword {
                        _state.update {
                            it.copy(
                                changePasswordStep = ChangePasswordStep.SetNew,
                                password = ""
                            )
                        }
                    }

                    ChangePasswordStep.SetNew -> updatePassword()
                }
            }

            PasswordMode.UNLOCK_NOTE -> {
                verifyPassword {
                    savedStateHandle.toRoute<SettingScreens.Password>().noteId?.let {
                        noteUseCases.setLockedNote(it, false)
                    }
                    _eventFlow.send(UIEvent.NavigateBack)
                }
            }

            PasswordMode.OPEN_NOTE -> {
                verifyPassword {
                    savedStateHandle.toRoute<SettingScreens.Password>().noteId?.let {
                        _eventFlow.send(UIEvent.NavigateToNoteDetails(it))
                    }
                }
            }

            PasswordMode.LOCK_NOTE_WITH_NEW_PASSWORD -> {
                updatePasswordUseCase(state.value.password)
                savedStateHandle.toRoute<SettingScreens.Password>().noteId?.let {
                    noteUseCases.setLockedNote(it, true)
                    _eventFlow.send(UIEvent.NavigateBack)
                }
            }
        }
    }

    private suspend fun updatePassword() {
        updatePasswordUseCase(state.value.password)
        _eventFlow.send(UIEvent.NavigateBack)
    }

    private suspend fun verifyPassword(onVerified: suspend () -> Unit) {
        val passwordVerified = verifyPasswordUseCase(state.value.password)
        _state.update { it.copy(passwordHasError = passwordVerified.not()) }
        if (passwordVerified) onVerified()
    }

    sealed class UIEvent {
        object NavigateBack : UIEvent()
        data class NavigateToNoteDetails(val noteId: Int) : UIEvent()
    }
}
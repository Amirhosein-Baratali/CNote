package com.baratali.cnote.feature_task.presentation.add_edit_task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.baratali.cnote.core.presentation.BaseViewModel
import com.baratali.cnote.feature_task.data.data_source.model.InvalidTaskException
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.domain.use_case.categories.CategoryUseCases
import com.baratali.cnote.feature_task.domain.use_case.tasks.TaskUseCases
import com.baratali.cnote.feature_task.presentation.util.TaskScreens
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var getTaskJob: Job? = null
    private var saveTaskJob: Job? = null

    private val _state = MutableStateFlow(AddEditTaskState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private var currentTask: Task? = null

    init {
        updateDatePickerTypeFromSetting()
        savedStateHandle.toRoute<TaskScreens.AddEditTask>().taskId?.let { taskId ->
            _state.update { it.copy(currentTaskId = taskId) }
            getTask(taskId)
        }
    }

    private fun updateDatePickerTypeFromSetting() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    datePickerType = dataStoreRepository.getSettings().first().datePickerType
                )
            }
        }
    }

    fun updateSelectedCategory(selectedCategoryId: Int?) {
        viewModelScope.launch {
            categoryUseCases.getCategory(selectedCategoryId).also { category ->
                _state.update { it.copy(selectedCategory = category) }
            }
        }
    }

    private fun getTask(id: Int) {
        getTaskJob?.cancel()
        getTaskJob = viewModelScope.launch {
            taskUseCases.getTaskWithCategory(id)?.also { taskWithCategory ->
                currentTask = taskWithCategory.task
                _state.update {
                    it.copy(
                        name = currentTask!!.name,
                        description = currentTask!!.description,
                        date = currentTask!!.date,
                        priority = currentTask!!.priority,
                        currentTaskId = currentTask!!.id
                    )
                }
                updateSelectedCategory(currentTask?.categoryId)
            }
        }
    }

    private fun saveTask() {
        saveTaskJob?.cancel()
        saveTaskJob = viewModelScope.launch {
            try {
                taskUseCases.addTask(
                    state.value.run {
                        Task(
                            name = name,
                            description = description,
                            timeCreated = System.currentTimeMillis(),
                            completed = currentTask?.completed == true,
                            id = currentTaskId,
                            priority = priority,
                            date = date,
                            categoryId = selectedCategory?.id
                        )
                    }
                )
                _eventFlow.send(UIEvent.NavigateUp)
            } catch (e: InvalidTaskException) {
                _eventFlow.send(UIEvent.ShowError(e.message))
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.NameChanged -> {
                _state.update { it.copy(name = event.name) }
            }

            is AddEditTaskEvent.DescriptionChanged -> {
                _state.update { it.copy(description = event.description) }
            }

            is AddEditTaskEvent.SaveTask -> {
                if (state.value.date != null) {
                    viewModelScope.launch {
                        _eventFlow.send(UIEvent.CheckForNotification)
                    }
                } else {
                    saveTask()
                }
            }

            AddEditTaskEvent.Dismiss -> {
                viewModelScope.launch {
                    _eventFlow.send(UIEvent.NavigateUp)
                }
            }

            AddEditTaskEvent.DateClicked -> {
                _state.update { it.copy(showDatePicker = true) }
            }

            AddEditTaskEvent.DateDismissed -> _state.update { it.copy(showDatePicker = false) }
            is AddEditTaskEvent.DateSelected -> _state.update { it.copy(date = event.date) }

            is AddEditTaskEvent.PrioritySelected -> {
                _state.update { it.copy(priority = event.priority) }
            }

            AddEditTaskEvent.CategoryClicked -> viewModelScope.launch {
                _eventFlow.send(UIEvent.NavigateToCategoryPicker)
            }

            is AddEditTaskEvent.NotificationPermissionResult -> {
                viewModelScope.launch {
                    dataStoreRepository.updateNotificationsEnabled(event.isGranted)
                }.invokeOnCompletion {
                    saveTask()
                }
            }
        }
    }

    sealed class UIEvent {
        object NavigateUp : UIEvent()
        data class ShowError(val message: String?) : UIEvent()
        object NavigateToCategoryPicker : UIEvent()
        object CheckForNotification : UIEvent()
    }

    override fun onCleared() {
        saveTaskJob?.cancel()
        getTaskJob?.cancel()
        super.onCleared()
    }
}
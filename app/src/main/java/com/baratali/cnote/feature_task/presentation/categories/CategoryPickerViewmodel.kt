package com.baratali.cnote.feature_task.presentation.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.baratali.cnote.core.presentation.BaseViewModel
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import com.baratali.cnote.feature_task.domain.use_case.categories.CategoryUseCases
import com.baratali.cnote.feature_task.presentation.util.TaskScreens
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
class CategoryPickerViewmodel @Inject constructor(
    private val useCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow(CategoryPickerState())
    val state = _state.asStateFlow()

    private var getCategoriesJob: Job? = null
    private var deleteCategoryJob: Job? = null

    init {
        getCategories()
        savedStateHandle.toRoute<TaskScreens.Categories>().categoryId?.let { categoryId ->
            _state.update { it.copy(selectedCategoryId = categoryId) }
        }
    }

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: CategoryPickerEvent) {
        when (event) {
            CategoryPickerEvent.AddNewCategory -> viewModelScope.launch {
                _eventFlow.send(UIEvent.NavigateToCategoryCreation)
            }

            is CategoryPickerEvent.CategorySelected -> {
                updateSelectedCategory(event.category.id)
            }

            CategoryPickerEvent.Dismiss -> viewModelScope.launch {
                _eventFlow.send(UIEvent.NavigateUp)
            }

            CategoryPickerEvent.EnterEditMode -> {
                _state.update { it.copy(isEditMode = true) }
            }

            CategoryPickerEvent.ExitEditMode -> {
                _state.update { it.copy(isEditMode = false) }
            }

            is CategoryPickerEvent.DeleteCategory -> {
                deleteCategory(event.category)
            }

            CategoryPickerEvent.NoCategorySelected -> {
                updateSelectedCategory(null)
            }
        }
    }

    private fun updateSelectedCategory(categoryId: Int? = null) {
        _state.update { it.copy(selectedCategoryId = categoryId) }
        navigateUpWithResult()
    }

    private fun navigateUpWithResult() {
        viewModelScope.launch {
            _eventFlow.send(UIEvent.NavigateUpWithResult)
        }
    }

    private fun deleteCategory(category: TaskCategory) {
        deleteCategoryJob?.cancel()
        deleteCategoryJob = viewModelScope.launch {
            useCases.deleteCategory(category)
        }
        getCategories()
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = viewModelScope.launch {
            useCases.getCategories().collect { categories ->
                _state.update { currentState ->
                    currentState.copy(
                        categories = categories
                    )
                }
            }
        }
    }

    sealed class UIEvent {
        object NavigateToCategoryCreation : UIEvent()
        object NavigateUp : UIEvent()
        object NavigateUpWithResult : UIEvent()
    }

    override fun onCleared() {
        getCategoriesJob?.cancel()
        deleteCategoryJob?.cancel()
        super.onCleared()
    }
}
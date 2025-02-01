package com.baratali.cnote.feature_task.presentation.category_creation

import com.baratali.cnote.feature_task.data.data_source.model.CategoryColor
import com.baratali.cnote.feature_task.data.data_source.model.CategoryIcon

sealed class CategoryCreationEvent {
    data class NameChanged(val name: String) : CategoryCreationEvent()
    data class IconSelected(val icon: CategoryIcon) : CategoryCreationEvent()
    data class ColorSelected(val color: CategoryColor) : CategoryCreationEvent()
    object SaveCategory : CategoryCreationEvent()
    object Dismiss : CategoryCreationEvent()
}

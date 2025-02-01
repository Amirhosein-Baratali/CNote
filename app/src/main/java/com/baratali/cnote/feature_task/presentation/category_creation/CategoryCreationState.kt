package com.baratali.cnote.feature_task.presentation.category_creation

import com.baratali.cnote.feature_task.data.data_source.model.CategoryColor
import com.baratali.cnote.feature_task.data.data_source.model.CategoryIcon

data class CategoryCreationState(
    val name: String = "",
    val selectedIcon: CategoryIcon = CategoryIcon.DEFAULT,
    val selectedColor: CategoryColor = CategoryColor.Default
) {
    val inputsValid
        get() = name.isNotBlank()
}
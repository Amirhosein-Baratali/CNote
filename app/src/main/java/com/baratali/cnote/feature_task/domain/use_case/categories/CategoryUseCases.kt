package com.baratali.cnote.feature_task.domain.use_case.categories

data class CategoryUseCases(
    val getCategories: GetCategories,
    val getCategory: GetCategory,
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory
)
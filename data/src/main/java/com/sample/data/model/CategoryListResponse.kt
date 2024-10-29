package com.sample.data.model

import com.sample.domain.model.CategoryListModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponse(
    val `data`: List<CategoryModelData>,
) {
    fun toCategoryList() = CategoryListModel(
        data = `data`.map { it.toCategory() }
    )
}


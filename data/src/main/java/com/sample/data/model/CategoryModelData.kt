package com.sample.data.model

import com.sample.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryModelData(
    val id: Int,
    val title: String,
    val image: String
) {
    fun toCategory() = Category(
        id = id,
        title = title,
        image = image
    )
}

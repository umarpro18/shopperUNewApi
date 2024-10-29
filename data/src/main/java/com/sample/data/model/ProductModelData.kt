package com.sample.data.model

import com.sample.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductModelData(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val categoryId: Int,
    val image: String
) {
    fun toProduct() = Product(
        id = id,
        title = title,
        price = price,
        categoryId = categoryId,
        description = description,
        image = image
    )
}

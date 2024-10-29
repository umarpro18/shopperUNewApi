package com.sample.data.model

import com.sample.domain.model.ProductListModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val `data`: List<ProductModelData>,
) {
    fun toProductList() = ProductListModel(
        products = `data`.map { it.toProduct() }
    )
}

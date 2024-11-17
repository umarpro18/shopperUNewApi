package com.sample.data.model

import com.sample.domain.model.CartModel
import kotlinx.serialization.Serializable

@Serializable
data class CartModelData(
    val id: Int,
    val productId: Int,
    val productName: String,
    val price: Double,
    val imageUrl: String? = null,
    val quantity: Int
) {
    fun toCart() = CartModel(
        id = id,
        productId = productId,
        price = price,
        imageUrl = imageUrl,
        productName = productName,
        quantity = quantity
    )
}

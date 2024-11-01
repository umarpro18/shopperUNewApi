package com.sample.data.model

import com.sample.domain.model.CartModel
import kotlinx.serialization.Serializable
import kotlin.Int

@Serializable
data class CartModelData(
    val id: Int,
    val productId: Int,
    val productName: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val userId: Int
) {
    fun toCart() = CartModel(
        id = id,
        productId = productId,
        productName = productName,
        name = name,
        price = price,
        imageUrl = imageUrl,
        quantity = quantity,
        userId = userId
    )
}

package com.sample.data.model

import com.sample.domain.model.request.CartRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int
) {
    companion object {
        fun requestAddToCart(addToCartRequest: CartRequestModel) = AddToCartRequest(
            userId = addToCartRequest.userId,
            productId = addToCartRequest.productId,
            price = addToCartRequest.price,
            quantity = addToCartRequest.quantity,
            productName = addToCartRequest.productName
        )
    }
}

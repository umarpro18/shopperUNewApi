package com.sample.data.model

import com.sample.domain.model.request.CartRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val productId: Int,
    val quantity: Int,
) {
    companion object {
        fun requestAddToCart(addToCartRequest: CartRequestModel) = AddToCartRequest(
            productId = addToCartRequest.productId,
            quantity = addToCartRequest.quantity,
        )
    }
}

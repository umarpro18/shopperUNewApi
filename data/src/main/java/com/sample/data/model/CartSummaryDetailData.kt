package com.sample.data.model

import com.sample.domain.model.CartSummaryDetailModel
import kotlinx.serialization.Serializable

@Serializable
data class CartSummaryDetailData(
    val items: List<CartModelData>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val shipping: Double,
    val discount: Double
) {
    fun toCartSummeryDetailData() = CartSummaryDetailModel(
        items = items.map { it.toCart() },
        subtotal = subtotal,
        tax = tax,
        total = total,
        shipping = shipping,
        discount = discount
    )
}

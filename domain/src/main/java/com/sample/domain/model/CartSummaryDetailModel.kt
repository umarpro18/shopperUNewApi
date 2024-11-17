package com.sample.domain.model

data class CartSummaryDetailModel(
    val items: List<CartModel>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val shipping: Double,
    val discount: Double
)

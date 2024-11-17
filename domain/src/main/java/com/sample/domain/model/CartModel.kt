package com.sample.domain.model

data class CartModel(
    val id: Int,
    val productName: String,
    val productId: Int,
    val price: Double,
    val imageUrl: String? = null,
    val quantity: Int
)

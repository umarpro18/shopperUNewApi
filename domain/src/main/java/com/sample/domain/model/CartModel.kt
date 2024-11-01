package com.sample.domain.model

data class CartModel(
    val id: Int,
    val productId: Int,
    val productName: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val userId: Int
)

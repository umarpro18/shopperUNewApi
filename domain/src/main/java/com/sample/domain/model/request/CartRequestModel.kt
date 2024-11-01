package com.sample.domain.model.request

data class CartRequestModel(
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int
)

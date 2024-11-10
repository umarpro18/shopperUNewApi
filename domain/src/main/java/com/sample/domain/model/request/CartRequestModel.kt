package com.sample.domain.model.request

data class CartRequestModel(
    val productId: Int,
    val price: Double,
    val quantity: Int,
)

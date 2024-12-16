package com.sample.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceOrderResponse(
    val `data`: OrderData,
)
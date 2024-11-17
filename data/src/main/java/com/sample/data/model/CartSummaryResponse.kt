package com.sample.data.model

import com.sample.domain.model.CartSummaryModel
import kotlinx.serialization.Serializable

@Serializable
data class CartSummaryResponse(
    val data: CartSummaryDetailData
) {
    fun toCartSummary() = CartSummaryModel(
        data =  data.toCartSummeryDetailData()
    )
}

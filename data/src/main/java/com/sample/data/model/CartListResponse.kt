package com.sample.data.model

import com.sample.domain.model.CartListModel
import kotlinx.serialization.Serializable

@Serializable
data class CartListResponse(
    val data: List<CartModelData>,
) {
    fun toCartList() = CartListModel(
        data = `data`.map { it.toCart() }
    )
}

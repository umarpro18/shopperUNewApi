package com.sample.domain.repository

import com.sample.domain.model.CartListModel
import com.sample.domain.model.request.CartRequestModel
import com.sample.domain.network.ResultWrapper

interface CartRepository {
    suspend fun addProductToCart(request: CartRequestModel): ResultWrapper<CartListModel>
}
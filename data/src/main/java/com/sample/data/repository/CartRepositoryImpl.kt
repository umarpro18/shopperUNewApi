package com.sample.data.repository

import com.sample.domain.model.CartListModel
import com.sample.domain.model.CartModel
import com.sample.domain.model.CartSummaryModel
import com.sample.domain.model.request.CartRequestModel
import com.sample.domain.network.NetworkService
import com.sample.domain.network.ResultWrapper
import com.sample.domain.repository.CartRepository

class CartRepositoryImpl(val networkService: NetworkService) : CartRepository {
    override suspend fun addProductToCart(request: CartRequestModel): ResultWrapper<CartListModel> {
        return networkService.addProductToCart(request)
    }

    override suspend fun getCartList(): ResultWrapper<CartListModel> {
        return networkService.getCartList()
    }

    override suspend fun updateCartItemQuantity(cartModel: CartModel): ResultWrapper<CartListModel> {
        return networkService.updateCartItemQuantity(cartModel)
    }

    override suspend fun deleteCartItem(
        cartItemId: Int,
        userId: Int
    ): ResultWrapper<CartListModel> {
        return networkService.deleteCartItem(cartItemId, userId)
    }

    override suspend fun getCartSummary(userId: Int): ResultWrapper<CartSummaryModel> {
        return networkService.getCartSummary(userId)
    }
}
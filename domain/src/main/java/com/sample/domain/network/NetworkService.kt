package com.sample.domain.network

import com.sample.domain.model.CartListModel
import com.sample.domain.model.CartModel
import com.sample.domain.model.CartSummaryModel
import com.sample.domain.model.CategoryListModel
import com.sample.domain.model.ProductListModel
import com.sample.domain.model.request.CartRequestModel

interface NetworkService {
    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>
    suspend fun getCategories(): ResultWrapper<CategoryListModel>
    suspend fun addProductToCart(request: CartRequestModel): ResultWrapper<CartListModel>
    suspend fun getCartList(): ResultWrapper<CartListModel>
    suspend fun updateCartItemQuantity(cartModel: CartModel): ResultWrapper<CartListModel>
    suspend fun deleteCartItem(cartItemId: Int, userId: Int): ResultWrapper<CartListModel>
    suspend fun getCartSummary(userId: Int): ResultWrapper<CartSummaryModel>
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}
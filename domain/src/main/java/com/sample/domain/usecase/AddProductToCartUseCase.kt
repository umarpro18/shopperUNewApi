package com.sample.domain.usecase

import com.sample.domain.model.request.CartRequestModel
import com.sample.domain.repository.CartRepository

class AddProductToCartUseCase(val repository: CartRepository) {
    suspend fun execute(request: CartRequestModel) = repository.addProductToCart(request = request)
}
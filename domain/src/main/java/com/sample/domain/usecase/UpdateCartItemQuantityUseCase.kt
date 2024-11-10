package com.sample.domain.usecase

import com.sample.domain.model.CartModel
import com.sample.domain.repository.CartRepository

class UpdateCartItemQuantityUseCase(val cartRepository: CartRepository) {
    suspend fun execute(cartModel: CartModel) = cartRepository.updateCartItemQuantity(cartModel)
}
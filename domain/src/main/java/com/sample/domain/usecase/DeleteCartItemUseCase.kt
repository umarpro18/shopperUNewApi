package com.sample.domain.usecase

import com.sample.domain.repository.CartRepository

class DeleteCartItemUseCase(val cartRepository: CartRepository) {
    suspend fun execute(cartItemId: Int, userId: Int) =
        cartRepository.deleteCartItem(cartItemId, userId)
}
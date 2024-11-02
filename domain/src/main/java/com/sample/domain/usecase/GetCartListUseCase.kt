package com.sample.domain.usecase

import com.sample.domain.repository.CartRepository

class GetCartListUseCase(val cartRepository: CartRepository) {
    suspend fun execute() = cartRepository.getCartList()
}
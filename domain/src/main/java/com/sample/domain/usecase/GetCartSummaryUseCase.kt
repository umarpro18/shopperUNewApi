package com.sample.domain.usecase

import com.sample.domain.repository.CartRepository

class GetCartSummaryUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(userId: Int) = cartRepository.getCartSummary(userId)
}
package com.sample.domain.usecase

import com.sample.domain.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {
    suspend fun execute(category: Int?) = repository.getProducts(category)
}
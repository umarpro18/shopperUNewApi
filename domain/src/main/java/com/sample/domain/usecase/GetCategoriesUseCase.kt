package com.sample.domain.usecase

import com.sample.domain.repository.CategoriesRepository

class GetCategoriesUseCase(private val repository: CategoriesRepository) {
    suspend fun execute() = repository.getCategories()
}
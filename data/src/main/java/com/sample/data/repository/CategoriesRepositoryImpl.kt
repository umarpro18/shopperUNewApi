package com.sample.data.repository

import com.sample.domain.network.NetworkService
import com.sample.domain.network.ResultWrapper
import com.sample.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl(private val networkService: NetworkService): CategoriesRepository {
    override suspend fun getCategories(): ResultWrapper<List<String>> {
        return networkService.getCategories()
    }
}
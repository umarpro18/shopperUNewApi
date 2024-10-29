package com.sample.data.repository

import com.sample.domain.model.Product
import com.sample.domain.model.ProductListModel
import com.sample.domain.network.NetworkService
import com.sample.domain.network.ResultWrapper
import com.sample.domain.repository.ProductRepository

class ProductRepositoryImpl(private val networkService: NetworkService) : ProductRepository {
    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {
        return networkService.getProducts(category)
    }
}
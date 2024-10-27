package com.sample.domain.repository

import com.sample.domain.model.Product
import com.sample.domain.model.ProductListModel
import com.sample.domain.network.ResultWrapper

interface ProductRepository {
    suspend fun getProducts(category: String?): ResultWrapper<List<Product>>
}
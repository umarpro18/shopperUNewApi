package com.sample.domain.network

import com.sample.domain.model.Product
import com.sample.domain.model.ProductListModel

interface NetworkService {
    suspend fun getProducts(category: String?): ResultWrapper<List<Product>>
    suspend fun getCategories(): ResultWrapper<List<String>>
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Failure(val exception: Exception): ResultWrapper<Nothing>()
}
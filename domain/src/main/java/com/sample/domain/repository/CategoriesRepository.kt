package com.sample.domain.repository

import com.sample.domain.model.CategoryListModel
import com.sample.domain.model.Product
import com.sample.domain.model.ProductListModel
import com.sample.domain.network.ResultWrapper

interface CategoriesRepository {
    suspend fun getCategories(): ResultWrapper<CategoryListModel>
}
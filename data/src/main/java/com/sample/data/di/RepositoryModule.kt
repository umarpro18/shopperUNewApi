package com.sample.data.di

import com.sample.data.repository.CartRepositoryImpl
import com.sample.data.repository.CategoriesRepositoryImpl
import com.sample.data.repository.ProductRepositoryImpl
import com.sample.domain.repository.CartRepository
import com.sample.domain.repository.CategoriesRepository
import com.sample.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    single<CategoriesRepository> {
        CategoriesRepositoryImpl(get())
    }

    single<CartRepository> {
        CartRepositoryImpl(get())
    }
}
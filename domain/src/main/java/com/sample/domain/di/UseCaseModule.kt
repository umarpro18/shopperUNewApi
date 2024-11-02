package com.sample.domain.di

import com.sample.domain.usecase.AddProductToCartUseCase
import com.sample.domain.usecase.GetCartListUseCase
import com.sample.domain.usecase.GetCategoriesUseCase
import com.sample.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { AddProductToCartUseCase(get()) }
    factory { GetCartListUseCase(get()) }
}
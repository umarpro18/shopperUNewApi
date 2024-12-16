package com.sample.domain.di

import com.sample.domain.usecase.AddProductToCartUseCase
import com.sample.domain.usecase.DeleteCartItemUseCase
import com.sample.domain.usecase.GetCartListUseCase
import com.sample.domain.usecase.GetCartSummaryUseCase
import com.sample.domain.usecase.GetCategoriesUseCase
import com.sample.domain.usecase.GetProductUseCase
import com.sample.domain.usecase.PlaceOrderUseCase
import com.sample.domain.usecase.UpdateCartItemQuantityUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { AddProductToCartUseCase(get()) }
    factory { GetCartListUseCase(get()) }
    factory { UpdateCartItemQuantityUseCase(get()) }
    factory { DeleteCartItemUseCase(get()) }
    factory { GetCartSummaryUseCase(get()) }
    factory { PlaceOrderUseCase(get()) }
}
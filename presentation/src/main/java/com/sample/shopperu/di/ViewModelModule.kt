package com.sample.shopperu.di

import com.sample.shopperu.ui.feature.cart.CartListViewModel
import com.sample.shopperu.ui.feature.home.HomeViewModel
import com.sample.shopperu.ui.feature.product_detail.ProductDetailViewModel
import com.sample.shopperu.ui.feature.summary.CartSummaryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel() {
        HomeViewModel(get(), get())
    }

    viewModel() {
        ProductDetailViewModel(get())
    }

    viewModel() {
        CartListViewModel(get(), get(), get())
    }

    viewModel() {
        CartSummaryViewModel(get())
    }
}
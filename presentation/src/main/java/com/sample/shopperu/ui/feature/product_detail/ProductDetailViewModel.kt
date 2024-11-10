package com.sample.shopperu.ui.feature.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.request.CartRequestModel
import com.sample.domain.network.ResultWrapper
import com.sample.domain.usecase.AddProductToCartUseCase
import com.sample.shopperu.uimodel.UiProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(val addProductToCartUseCase: AddProductToCartUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUIEvents>(ProductDetailUIEvents.Nothing)
    val uiState = _uiState.asStateFlow()

    fun addProductToCart(product: UiProductModel) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUIEvents.Loading

            val result = addProductToCartUseCase.execute(
                CartRequestModel(
                    productId = product.id,
                    price = product.price,
                    quantity = 1,
                )
            )

            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = ProductDetailUIEvents.Success("Product added to cart!")
                }

                is ResultWrapper.Failure -> {
                    _uiState.value =
                        ProductDetailUIEvents.Error("Something happened while adding product to cart, please retry!")
                }
            }
        }
    }
}

sealed class ProductDetailUIEvents() {
    object Loading : ProductDetailUIEvents()
    object Nothing : ProductDetailUIEvents()
    data class Success(val data: String) : ProductDetailUIEvents()
    data class Error(val message: String) : ProductDetailUIEvents()
}
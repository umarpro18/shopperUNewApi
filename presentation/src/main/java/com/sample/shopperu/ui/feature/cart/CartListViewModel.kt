package com.sample.shopperu.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.CartModel
import com.sample.domain.network.ResultWrapper
import com.sample.domain.usecase.DeleteCartItemUseCase
import com.sample.domain.usecase.GetCartListUseCase
import com.sample.domain.usecase.UpdateCartItemQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartListViewModel(
    private val cartListUseCase: GetCartListUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartListScreenUIEvents>(CartListScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCartList()
    }

    private fun getCartList() {
        viewModelScope.launch {
            val result = cartListUseCase.execute()
            _uiState.value = CartListScreenUIEvents.Loading
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = CartListScreenUIEvents.Success(result.value.data)
                }

                is ResultWrapper.Failure -> {
                    _uiState.value = CartListScreenUIEvents.Error("Please try later!")
                }
            }
        }
    }

    fun incrementQuantity(cartModel: CartModel) {
        if (cartModel.quantity == 10) return
        updateCartItemQuantity(cartModel.copy(quantity = cartModel.quantity + 1))
    }

    fun decrementQuantity(cartModel: CartModel) {
        if (cartModel.quantity == 1) return
        updateCartItemQuantity(cartModel.copy(quantity = cartModel.quantity - 1))
    }

    fun updateCartItemQuantity(cartModel: CartModel) {
        _uiState.value = CartListScreenUIEvents.Loading
        viewModelScope.launch {
            val result = updateCartItemQuantityUseCase.execute(cartModel)
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = CartListScreenUIEvents.Success(result.value.data)
                }

                is ResultWrapper.Failure -> {
                    _uiState.value = CartListScreenUIEvents.Error("Please try later!")
                }
            }
        }
    }

    fun deleteCartItem(cartModel: CartModel) {
        _uiState.value = CartListScreenUIEvents.Loading
        viewModelScope.launch {
            val result = deleteCartItemUseCase.execute(cartModel.id, 1)
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = CartListScreenUIEvents.Success(result.value.data)
                }

                is ResultWrapper.Failure -> {
                    _uiState.value = CartListScreenUIEvents.Error("Please try later!")
                }
            }
        }
    }

}

sealed class CartListScreenUIEvents {
    object Loading : CartListScreenUIEvents()
    data class Success(val cartListValue: List<CartModel>) : CartListScreenUIEvents()
    data class Error(val message: String) : CartListScreenUIEvents()
}
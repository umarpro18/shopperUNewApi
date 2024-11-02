package com.sample.shopperu.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.CartListModel
import com.sample.domain.model.CartModel
import com.sample.domain.network.ResultWrapper
import com.sample.domain.usecase.GetCartListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartListViewModel(val cartListUseCase: GetCartListUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<CartListScreenUIEvents>(CartListScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCartList()
    }

    fun getCartList() {
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
}

sealed class CartListScreenUIEvents {
    object Loading : CartListScreenUIEvents()
    data class Success(val cartListValue: List<CartModel>) : CartListScreenUIEvents()
    data class Error(val message: String) : CartListScreenUIEvents()
}
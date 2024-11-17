package com.sample.shopperu.ui.feature.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.CartSummaryModel
import com.sample.domain.network.ResultWrapper
import com.sample.domain.usecase.GetCartSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartSummaryViewModel(
    private val cartSummaryUseCase: GetCartSummaryUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CartSummaryScreenUIEvents>(CartSummaryScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCartSummary()
    }

    private fun getCartSummary() {
        viewModelScope.launch {
            val result = cartSummaryUseCase.execute(1)
            _uiState.value = CartSummaryScreenUIEvents.Loading
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = CartSummaryScreenUIEvents.Success(result.value)
                }

                is ResultWrapper.Failure -> {
                    _uiState.value = CartSummaryScreenUIEvents.Error("Please try later!")
                }
            }
        }
    }
}

sealed class CartSummaryScreenUIEvents {
    data object Loading : CartSummaryScreenUIEvents()
    data class Success(val cartSummaryData: CartSummaryModel) : CartSummaryScreenUIEvents()
    data class Error(val message: String) : CartSummaryScreenUIEvents()
}
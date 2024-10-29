package com.sample.shopperu.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.Category
import com.sample.domain.model.Product
import com.sample.domain.network.ResultWrapper
import com.sample.domain.usecase.GetCategoriesUseCase
import com.sample.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
            val featured = getProducts(1)
            val popularProducts = getProducts(2)
            val categories = getCategories()
            if (featured.isEmpty() && popularProducts.isEmpty() && categories.isEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("Retry, Something has happened..")
            } else {
                _uiState.value =
                    HomeScreenUIEvents.Success(UIState(featured, popularProducts, categories))
            }
        }
    }

    suspend fun getProducts(category: Int?): List<Product> {
        getProductUseCase.execute(category).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    val data = (result).value
                    return data.products
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }

    suspend fun getCategories(): List<Category> {
        getCategoriesUseCase.execute().let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    val data = (result).value
                    return data.data
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }
}

sealed class HomeScreenUIEvents {
    object Loading : HomeScreenUIEvents()
    data class Success(
        val uiState: UIState
    ) : HomeScreenUIEvents()

    data class Error(val error: String) : HomeScreenUIEvents()
}

data class UIState(
    val featured: List<Product>,
    val popularProducts: List<Product>,
    val categories: List<Category>
)


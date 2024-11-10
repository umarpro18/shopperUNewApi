package com.sample.data.network

import com.sample.data.model.AddToCartRequest
import com.sample.data.model.CartListResponse
import com.sample.data.model.CartModelData
import com.sample.data.model.ProductListResponse
import com.sample.domain.model.CartListModel
import com.sample.domain.model.CartModel
import com.sample.domain.model.CategoryListModel
import com.sample.domain.model.ProductListModel
import com.sample.domain.model.request.CartRequestModel
import com.sample.domain.network.NetworkService
import com.sample.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType

class NetworkServiceImpl(val client: HttpClient) : NetworkService {
    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2"
    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {
        val url =
            if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products"
        return makeHttpRequest(url = url,
            method = HttpMethod.Get,
            mapper = { dataModels: ProductListResponse ->
                dataModels.toProductList()
            })
    }

    override suspend fun getCategories(): ResultWrapper<CategoryListModel> {
        val url = "$baseUrl/categories"
        return makeHttpRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { dataModels: com.sample.data.model.CategoryListResponse ->
                dataModels.toCategoryList()
            }
        )
    }

    override suspend fun addProductToCart(request: CartRequestModel): ResultWrapper<CartListModel> {
        val url = "$baseUrl/cart/1"
        return makeHttpRequest(
            url = url,
            method = HttpMethod.Post,
            body = AddToCartRequest.requestAddToCart(request),
            mapper = { cartModel: CartListResponse ->
                cartModel.toCartList()
            }
        )
    }

    override suspend fun getCartList(): ResultWrapper<CartListModel> {
        val url = "$baseUrl/cart/1"
        return makeHttpRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { cartModel: CartListResponse ->
                cartModel.toCartList()
            }
        )
    }

    override suspend fun updateCartItemQuantity(cartModel: CartModel): ResultWrapper<CartListModel> {
        val url = "$baseUrl/cart/1/${cartModel.id}"
        return makeHttpRequest(
            url = url,
            method = HttpMethod.Put,
            body = AddToCartRequest(
                productId = cartModel.productId,
                quantity = cartModel.quantity
            ),
            mapper = { cartModel: CartListResponse ->
                cartModel.toCartList()
            }
        )
    }

    suspend inline fun <reified T, R> makeHttpRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method
                // Apply query parameters
                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }
                // Apply headers
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                // Set body for POST, PUT, etc.
                if (body != null) {
                    setBody(body)
                }
                // Set content type
                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: kotlinx.io.IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}
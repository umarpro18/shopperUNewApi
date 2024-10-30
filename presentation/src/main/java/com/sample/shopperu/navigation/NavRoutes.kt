package com.sample.shopperu.navigation

import com.sample.shopperu.uimodel.UiProductModel
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetails(val product: UiProductModel)
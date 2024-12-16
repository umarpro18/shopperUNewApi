package com.sample.shopperu.navigation

import com.sample.shopperu.uimodel.UiProductModel
import kotlinx.serialization.Serializable

// A common base class for navigation routes
@Serializable
sealed class BottomNavRoute

// Define routes as serializable objects
@Serializable
object HomeScreen : BottomNavRoute()

@Serializable
object CartScreen : BottomNavRoute()

@Serializable
object ProfileScreen : BottomNavRoute()

@Serializable
data class ProductDetailRoute(val product: UiProductModel)

@Serializable
object CartSummaryRoute

@Serializable
data class UserAddressRoute(val userAddressRouteWrapper: UserAddressRouteWrapper)
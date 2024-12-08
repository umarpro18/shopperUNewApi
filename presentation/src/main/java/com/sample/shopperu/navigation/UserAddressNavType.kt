package com.sample.shopperu.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val userAddressNavType = object : NavType<UserAddressRouteWrapper>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String
    ): UserAddressRouteWrapper? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, UserAddressRouteWrapper::class.java)
        } else {
            bundle.getParcelable(key) as? UserAddressRouteWrapper
        }
    }

    override fun parseValue(value: String): UserAddressRouteWrapper {
        return Json.decodeFromString<UserAddressRouteWrapper>(value)
    }

    override fun serializeAsValue(value: UserAddressRouteWrapper): String {
        return Json.encodeToString(
            value
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun put(
        bundle: Bundle,
        key: String,
        value: UserAddressRouteWrapper
    ) {
        bundle.putParcelable(key, value)
    }
}
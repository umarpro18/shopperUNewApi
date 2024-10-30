package com.sample.shopperu.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.sample.shopperu.uimodel.UiProductModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String
    ): UiProductModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, UiProductModel::class.java)
        } else {
            bundle.getParcelable(key) as? UiProductModel
        }
    }

    override fun parseValue(value: String): UiProductModel {
        val value = Json.decodeFromString<UiProductModel>(value)

        return value.copy(
            image = URLDecoder.decode(value.image, "UTF-8"),
            description = String(
                java.util.Base64.getDecoder().decode(value.description.toByteArray())
            ).replace("/", "_"),
            title = String(java.util.Base64.getDecoder().decode(value.title.toByteArray())).replace(
                "/",
                "_"
            )
        )
    }

    override fun serializeAsValue(value: UiProductModel): String {
        return Json.encodeToString(
            value.copy(
                image = URLEncoder.encode(value.image, "UTF-8"),
                description = String(
                    java.util.Base64.getEncoder().encode(value.description.toByteArray())
                ).replace("/", "_"),
                title = String(
                    java.util.Base64.getEncoder().encode(value.title.toByteArray())
                ).replace("/", "_")
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun put(
        bundle: Bundle,
        key: String,
        value: UiProductModel
    ) {
        bundle.putParcelable(key, value)
    }

}
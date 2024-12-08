package com.sample.shopperu.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddressModel(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
) : Parcelable {
    override fun toString(): String {
        return "$addressLine, $city, $state, $postalCode, $country"
    }
}

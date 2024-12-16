package com.sample.shopperu.uimodel

import android.os.Parcelable
import com.sample.domain.model.AddressDomainModel
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

    fun fromAddressDomainModel() = AddressDomainModel(
        addressLine = this.addressLine,
        city = this.city,
        state = this.state,
        postalCode = this.postalCode,
        country = this.country
    )
}

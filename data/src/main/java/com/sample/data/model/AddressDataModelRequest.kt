package com.sample.data.model

import com.sample.domain.model.AddressDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class AddressDataModelRequest(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
) {
    companion object {
        fun requestAddressModel(addressDomainModel: AddressDomainModel) = AddressDataModelRequest(
            addressLine = addressDomainModel.addressLine,
            city = addressDomainModel.city,
            state = addressDomainModel.state,
            postalCode = addressDomainModel.postalCode,
            country = addressDomainModel.country,
        )
    }
}

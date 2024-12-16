package com.sample.domain.repository

import com.sample.domain.model.AddressDomainModel
import com.sample.domain.network.ResultWrapper

interface OrderRepository {
    suspend fun placeOrder(addressDomainModel: AddressDomainModel): ResultWrapper<Long>
}
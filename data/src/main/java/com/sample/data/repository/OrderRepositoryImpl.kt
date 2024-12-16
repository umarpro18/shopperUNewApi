package com.sample.data.repository

import com.sample.domain.model.AddressDomainModel
import com.sample.domain.network.NetworkService
import com.sample.domain.network.ResultWrapper
import com.sample.domain.repository.OrderRepository

class OrderRepositoryImpl(private val networkService: NetworkService): OrderRepository {
    override suspend fun placeOrder(addressDomainModel: AddressDomainModel): ResultWrapper<Long> {
        return networkService.placeOrder(addressDomainModel, 1)
    }
}
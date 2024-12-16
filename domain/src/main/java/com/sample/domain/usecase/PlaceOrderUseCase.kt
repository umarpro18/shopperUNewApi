package com.sample.domain.usecase

import com.sample.domain.model.AddressDomainModel
import com.sample.domain.repository.OrderRepository

class PlaceOrderUseCase(val repository: OrderRepository) {
    suspend fun execute(addressDomainModel: AddressDomainModel) = repository.placeOrder(addressDomainModel)
}
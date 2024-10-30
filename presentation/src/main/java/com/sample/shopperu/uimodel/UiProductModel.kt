package com.sample.shopperu.uimodel

import android.os.Parcelable
import com.sample.domain.model.Product
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.Int

@Serializable
@Parcelize
data class UiProductModel(
    val id: Int,
    val title: String,
    val price: Double,
    val categoryId: Int,
    val description: String,
    val image: String
) : Parcelable {
    companion object {
        fun fromProduct(product: Product) = UiProductModel(
            id = product.id,
            title = product.title,
            price = product.price,
            categoryId = product.categoryId,
            description = product.description,
            image = product.image
        )
    }
}
package com.sample.shopperu.navigation

import android.os.Parcelable
import com.sample.shopperu.uimodel.UserAddressModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddressRouteWrapper(val userAddressModel: UserAddressModel?) : Parcelable
package com.sample.shopperu.ui.feature.user_address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sample.shopperu.uimodel.UserAddressModel


const val USER_ADDRESS_KEY = "user_address_key"

@Composable
fun UserAddressScreen(navController: NavController, userAddressModel: UserAddressModel?) {

    val addressLine = remember { mutableStateOf(userAddressModel?.addressLine ?: "") }
    val city = remember { mutableStateOf(userAddressModel?.city ?: "") }
    val state = remember { mutableStateOf(userAddressModel?.state ?: "") }
    val postalCode = remember { mutableStateOf(userAddressModel?.postalCode ?: "") }
    val country = remember { mutableStateOf(userAddressModel?.country ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = addressLine.value,
            onValueChange = { addressLine.value = it },
            label = { Text(text = "Address Line") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = city.value,
            onValueChange = { city.value = it },
            label = { Text(text = "City") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.value,
            onValueChange = { state.value = it },
            label = { Text(text = "State") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = postalCode.value,
            onValueChange = { postalCode.value = it },
            label = { Text(text = "Postal code") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = country.value,
            onValueChange = { country.value = it },
            label = { Text(text = "Country") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val addressData = UserAddressModel(
                    addressLine = addressLine.value,
                    city = city.value,
                    state = state.value,
                    postalCode = postalCode.value,
                    country = country.value
                )
                val previousBackStack = navController.previousBackStackEntry
                previousBackStack?.savedStateHandle?.set(USER_ADDRESS_KEY, addressData)
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth().padding(8.dp),
            enabled = addressLine.value.isNotEmpty() && country.value.isNotEmpty()
        ) {
            Text(text = "Save & Close")
        }
    }
}
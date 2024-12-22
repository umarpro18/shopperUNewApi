package com.sample.shopperu.ui.feature.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sample.domain.model.CartModel
import com.sample.domain.model.CartSummaryModel
import com.sample.shopperu.R
import com.sample.shopperu.navigation.HomeScreen
import com.sample.shopperu.navigation.UserAddressRoute
import com.sample.shopperu.navigation.UserAddressRouteWrapper
import com.sample.shopperu.ui.feature.user_address.USER_ADDRESS_KEY
import com.sample.shopperu.uimodel.UserAddressModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartSummaryScreen(
    navController: NavController,
    cartSummaryViewModel: CartSummaryViewModel = koinViewModel()
) {
    val uiState = cartSummaryViewModel.uiState.collectAsState()

    val addressData = remember {
        mutableStateOf<UserAddressModel?>(null)
    }
    val cartSummary = remember {
        mutableStateOf<CartSummaryModel?>(null)
    }
    val orderSuccessId = remember {
        mutableStateOf<Long?>(null)
    }
    val loading = remember {
        mutableStateOf(false)
    }
    val error = remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(navController) {
        val savedState = navController.currentBackStackEntry?.savedStateHandle
        savedState?.getStateFlow(USER_ADDRESS_KEY, addressData.value)?.collect { userAddress ->
            addressData.value = userAddress
        }
    }

    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is CartSummaryScreenUIEvents.Loading -> {
                loading.value = true
                error.value = null
            }

            is CartSummaryScreenUIEvents.Success -> {
                loading.value = false
                error.value = null
                val data = (uiState.value as CartSummaryScreenUIEvents.Success).cartSummaryData
                cartSummary.value = data
                orderSuccessId.value = null
            }

            is CartSummaryScreenUIEvents.Error -> {
                loading.value = false
                error.value = (uiState.value as CartSummaryScreenUIEvents.Error).message
                orderSuccessId.value = null
            }

            is CartSummaryScreenUIEvents.PlaceOrder -> {
                orderSuccessId.value =
                    (uiState.value as CartSummaryScreenUIEvents.PlaceOrder).orderId
                loading.value = false
                error.value = null
                cartSummary.value = null
            }
        }
    }
    CartSummaryContent(
        navController,
        addressData,
        cartSummary.value,
        loading.value,
        error.value,
        orderSuccessId.value,
        cartSummaryViewModel
    )
}

@Composable
fun CartSummaryContent(
    navController: NavController,
    userAddress: MutableState<UserAddressModel?>,
    cartSummaryModel: CartSummaryModel?,
    loading: Boolean,
    error: String?,
    orderSuccessId: Long?,
    cartSummaryViewModel: CartSummaryViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = "Cart Summary",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        val showSummaryData = (!loading && error == null)

        if (showSummaryData) cartSummaryModel?.let {
            Column {
                AddressBar(
                    userAddress.value?.toString() ?: "Please add one",
                    onClick = {
                        navController.navigate(
                            UserAddressRoute(
                                userAddressRouteWrapper = UserAddressRouteWrapper(userAddressModel = userAddress.value)
                            )
                        )
                    })
                Spacer(modifier = Modifier.size(8.dp))
                CartSummaryData(it, cartSummaryViewModel, userAddress)
            }
        }
    }

    if (orderSuccessId != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.ic_bag), contentDescription = null)
            Text(
                text = "Order Placed: $orderSuccessId",
                style = MaterialTheme.typography.titleMedium
            )

            Button(onClick = { navController.popBackStack(HomeScreen, inclusive = false) }) {
                Text(text = "Continue Shopping", style = MaterialTheme.typography.titleSmall)
            }
        }
    }

    if (loading) {
        LoadingContent()
    }

    if (error != null) {
        ErrorContent(error)
    }
}

@Composable
fun CartSummaryData(
    cartSummaryModel: CartSummaryModel,
    cartSummaryViewModel: CartSummaryViewModel,
    userAddress: MutableState<UserAddressModel?>
) {
    Box(
        modifier = Modifier.fillMaxSize() // Ensures the Box takes the entire screen space
    ) {
        // Background for content (if needed)
        LazyColumn(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(16.dp)
                .wrapContentHeight()
                .align(Alignment.TopStart) // Aligns LazyColumn to the top of the Box
        ) {
            item {
                Text(
                    text = "Order Summary",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            items(cartSummaryModel.data.items) { cartItem ->
                ProductRow(cartItem)
            }

            item {
                Column {
                    AmountRow("Items", cartSummaryModel.data.items.size.toDouble())
                    AmountRow("Subtotal", cartSummaryModel.data.subtotal)
                    AmountRow("Tax", cartSummaryModel.data.tax)
                    AmountRow("Shipping", cartSummaryModel.data.shipping)
                    AmountRow("Discount", cartSummaryModel.data.discount)
                    Spacer(modifier = Modifier.padding(8.dp))
                    AmountRow("Total", cartSummaryModel.data.total)
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .align(Alignment.BottomCenter), // Aligns the button at the bottom
            onClick = { userAddress.value?.let { cartSummaryViewModel.placeOrder(userAddressModel = it) } },
            shape = RoundedCornerShape(24.dp),
            enabled = userAddress.value != null
        ) {
            Text(
                text = "Confirm Order",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
fun ProductRow(cartModel: CartModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = cartModel.productName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${cartModel.price} x ${cartModel.quantity}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun AmountRow(title: String, amount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = amount.toString(), style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun AddressBar(address: String, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(24.dp))
        .padding(8.dp)
        .clickable { onClick.invoke() }) {
        Image(
            painter = painterResource(id = R.drawable.ic_address),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(40.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(
                text = "Shipping Address",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorContent(error: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
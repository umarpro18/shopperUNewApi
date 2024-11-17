package com.sample.shopperu.ui.feature.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sample.domain.model.CartModel
import com.sample.domain.model.CartSummaryModel
import com.sample.shopperu.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartSummaryScreen(cartSummaryViewModel: CartSummaryViewModel = koinViewModel()) {
    val uiState = cartSummaryViewModel.uiState.collectAsState()

    val cartSummary = remember {
        mutableStateOf<CartSummaryModel?>(null)
    }
    val loading = remember {
        mutableStateOf(false)
    }
    val error = remember {
        mutableStateOf<String?>(null)
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
            }

            is CartSummaryScreenUIEvents.Error -> {
                loading.value = false
                error.value = (uiState.value as CartSummaryScreenUIEvents.Error).message
            }
        }
    }
    CartSummaryContent(
        cartSummary.value,
        loading.value,
        error.value,
    )
}

@Composable
fun CartSummaryContent(
    cartSummaryModel: CartSummaryModel?,
    loading: Boolean,
    error: String?,
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

        if (showSummaryData) cartSummaryModel?.let { CartSummaryData(it) }

        if (loading) {
            LoadingContent()
        }

        if (error != null) {
            ErrorContent(error)
        }
    }
}

@Composable
fun CartSummaryData(cartSummaryModel: CartSummaryModel) {
    LazyColumn {
        item {
            Text(text = "Products", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
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
fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
package com.sample.shopperu.ui.feature.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import com.sample.domain.model.CartModel
import com.sample.shopperu.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartListScreen(cartViewModel: CartListViewModel = koinViewModel()) {

    val uiState = cartViewModel.uiState.collectAsState()
    val cartList = remember {
        mutableStateOf(emptyList<CartModel>())
    }
    val loading = remember {
        mutableStateOf(false)
    }
    val error = remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is CartListScreenUIEvents.Loading -> {
                loading.value = true
                error.value = null
            }

            is CartListScreenUIEvents.Success -> {
                loading.value = false
                error.value = null
                val data = (uiState.value as CartListScreenUIEvents.Success).cartListValue
                if (data.isEmpty()) {
                    error.value = "No Items in the Cart!"
                } else {
                    cartList.value = data
                }
            }

            is CartListScreenUIEvents.Error -> {
                loading.value = false
                error.value = (uiState.value as CartListScreenUIEvents.Error).message
            }
        }
    }
    CartContent(cartList.value, loading.value, error.value, cartViewModel)
}

@Composable
fun CartContent(
    cartList: List<CartModel>,
    loading: Boolean,
    error: String?,
    cartViewModel: CartListViewModel
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
                text = "Cart",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        val shouldShowList = (!loading && error == null)
        AnimatedVisibility(
            visible = shouldShowList,
            enter = fadeIn(),
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn {
                items(cartList) { item ->
                    CartItem(
                        item,
                        onIncrement = { cartViewModel.incrementQuantity(item) },
                        onDecrement = { cartViewModel.decrementQuantity(item) },
                        onDelete = { cartViewModel.deleteCartItem(item) })
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

@Composable
fun CartItem(
    item: CartModel, onIncrement: (CartModel) -> Unit,
    onDecrement: (CartModel) -> Unit, onDelete: (CartModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {

        AsyncImage(
            model = item.imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier.size(126.dp, 96.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 8.dp) // Added padding to replace Spacers
        ) {
            Text(
                text = item.productName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            )
            Spacer(modifier = Modifier.size(4.dp)) // Minor space between text lines
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = { onDelete(item) }) {
                Image(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete Item"
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecrement(item) }) {
                    Image(
                        painter = painterResource(R.drawable.ic_minus),
                        contentDescription = "Decrease Quantity"
                    )
                }

                Text(
                    text = item.quantity.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = { onIncrement(item) }) {
                    Image(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = "Increase Quantity"
                    )
                }
            }
        }
    }
}
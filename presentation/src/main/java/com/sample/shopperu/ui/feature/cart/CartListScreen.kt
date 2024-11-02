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
fun CartListScreen(viewModel: CartListViewModel = koinViewModel()) {

    val uiState = viewModel.uiState.collectAsState()
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
    CartContent(cartList.value, loading.value, error.value)
}

@Composable
fun CartContent(cartList: List<CartModel>, loading: Boolean, error: String?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(8.dp))

                val shouldShowList = (!loading && error == null)
                AnimatedVisibility(
                    visible = shouldShowList,
                    enter = fadeIn(),
                    modifier = Modifier.weight(1f)
                ) {
                    LazyColumn {
                        items(cartList) { item ->
                            CartItem(item)
                        }
                    }
                }

                if (loading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }

                if (error != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItem(item: CartModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {

        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(126.dp, 96.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = item.productName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {

            IconButton(onClick = {}) {
                Image(painter = painterResource(R.drawable.ic_delete), contentDescription = null)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    Image(painter = painterResource(R.drawable.ic_minus), contentDescription = null)
                }

                Text(text = item.quantity.toString())

                IconButton(onClick = {}) {
                    Image(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
                }

            }
        }
    }

}
package com.sample.shopperu.ui.feature.product_detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.shopperu.R
import com.sample.shopperu.uimodel.UiProductModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailScreen(
    product: UiProductModel,
    viewModel: ProductDetailViewModel = koinViewModel()
) {
    val loading = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val uiState = viewModel.uiState.collectAsState()
        when (uiState.value) {
            is ProductDetailUIEvents.Loading -> {
                loading.value = true
            }

            is ProductDetailUIEvents.Success -> {
                val result = uiState.value as (ProductDetailUIEvents.Success)
                Toast.makeText(LocalContext.current, result.data, Toast.LENGTH_SHORT).show()
                loading.value = true
            }

            is ProductDetailUIEvents.Error -> {
                val result = uiState.value as (ProductDetailUIEvents.Error)
                Toast.makeText(LocalContext.current, result.message, Toast.LENGTH_SHORT).show()
                loading.value = true
            }

            else -> {
                loading.value = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp)
                    .clickable { },
            )
            Image(
                painter = painterResource(R.drawable.ic_favorite),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp)
                    .clickable { }
            )
        }
        Spacer(Modifier.size(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
                Text(
                    text = "(10 Reviews)",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6,
                minLines = 3,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Size",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(16.dp)
            )
            Spacer(Modifier.size(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { viewModel.addProductToCart(product) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Buy Now",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White,
                    )
                }
                Spacer(Modifier.size(16.dp))
                IconButton(
                    onClick = { viewModel.addProductToCart(product) },
                    colors = IconButtonDefaults.iconButtonColors()
                        .copy(containerColor = Color.LightGray.copy(alpha = 0.3f))
                ) {
                    Image(painter = painterResource(R.drawable.ic_cart), contentDescription = null)
                }
            }
        }
    }
}

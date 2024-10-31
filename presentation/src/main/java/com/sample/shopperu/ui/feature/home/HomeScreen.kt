package com.sample.shopperu.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.domain.model.Category
import com.sample.domain.model.Product
import com.sample.shopperu.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(onClick: (Product) -> Unit, viewModel: HomeViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    val featured = remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    val popularProducts = remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    val categories = remember {
        mutableStateOf<List<Category>>(emptyList())
    }

    val isLoading = remember {
        mutableStateOf<Boolean>(false)
    }

    val error = remember {
        mutableStateOf<String?>(null)
    }

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState.value) {
                is HomeScreenUIEvents.Loading -> {
                    isLoading.value = true
                    error.value = null
                }

                is HomeScreenUIEvents.Success -> {
                    val data = (uiState.value as HomeScreenUIEvents.Success)
                    error.value = null
                    isLoading.value = false
                    featured.value = data.uiState.featured
                    popularProducts.value = data.uiState.popularProducts
                    categories.value = data.uiState.categories
                }

                is HomeScreenUIEvents.Error -> {
                    val data = (uiState.value as HomeScreenUIEvents.Error)
                    error.value = data.error
                    isLoading.value = false
                }
            }
            HomeContent(
                featured.value,
                popularProducts.value,
                categories.value,
                isLoading.value,
                error.value,
                onClick = onClick
            )
        }
    }
}

@Composable
fun DisplayLoading(isLoading: Boolean) {
    if (isLoading == true) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DisplayError(error: String?) {
    error?.let {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(text = it, style = MaterialTheme.typography.bodyMedium, color = Color.Red)
        }
    }
}

@Composable
fun HomeContent(
    featured: List<Product>,
    popularProducts: List<Product>,
    categories: List<Category>,
    isLoading: Boolean,
    error: String?,
    onClick: (Product) -> Unit
) {
    LazyColumn {
        item {
            ProfileHeader()
            Spacer(modifier = Modifier.size(16.dp))
            SearchBar(value = "", onTextChanged = {})
            Spacer(modifier = Modifier.size(16.dp))
            DisplayLoading(isLoading)
            DisplayError(error)
            CategoryRow(categories)
        }
        item {
            if (featured.isNotEmpty()) {
                HomeProductRow(featured, "Featured", onClick = onClick)
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popularProducts.isNotEmpty()) {
                HomeProductRow(popularProducts, "Popular", onClick = onClick)
            }
        }
    }

}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_img),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Hello!", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "John Umar",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_bell),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f)),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun SearchBar(value: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onTextChanged,
        placeholder = { Text("Type to search", style = MaterialTheme.typography.bodySmall) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
        )
    )
}


@Composable
fun CategoryRow(categories: List<Category>) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        items(categories, key = { it.id }) { category ->
            val isVisible = remember {
                mutableStateOf(false)
            }
            LaunchedEffect(true) {
                isVisible.value = true
            }
            AnimatedVisibility(visible = isVisible.value, enter = fadeIn() + expandVertically()) {
                Text(
                    text = category.title.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                )
            }
        }
    }
    Spacer(
        modifier = Modifier.size(16.dp)
    )
}


@Composable
fun HomeProductRow(products: List<Product>, title: String, onClick: (Product) -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "View All",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            items(products, key = { it.id }) { product ->
                val isVisible = remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(true) {
                    isVisible.value = true
                }
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn() + expandVertically()
                ) {
                    ProductItem(product, onClick = onClick)
                }
            }
        }

    }
}

@Composable
fun ProductItem(productValue: Product, onClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(width = 126.dp, height = 144.dp)
            .clickable { onClick(productValue) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = productValue.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = productValue.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "$${productValue.price}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

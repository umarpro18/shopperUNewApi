package com.sample.shopperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sample.shopperu.navigation.CartScreen
import com.sample.shopperu.navigation.CartSummaryRoute
import com.sample.shopperu.navigation.HomeScreen
import com.sample.shopperu.navigation.ProductDetailRoute
import com.sample.shopperu.navigation.ProfileScreen
import com.sample.shopperu.navigation.productNavType
import com.sample.shopperu.ui.feature.cart.CartListScreen
import com.sample.shopperu.ui.feature.home.HomeScreen
import com.sample.shopperu.ui.feature.product_detail.ProductDetailScreen
import com.sample.shopperu.ui.feature.summary.CartSummaryScreen
import com.sample.shopperu.ui.theme.ShopperUTheme
import com.sample.shopperu.uimodel.UiProductModel
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopperUTheme {
                val shouldShowBottomBar = remember {
                    mutableStateOf(true)
                }
                val navController = rememberNavController()
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(visible = shouldShowBottomBar.value) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        NavHost(navController = navController, startDestination = HomeScreen) {
                            composable<HomeScreen> {
                                HomeScreen(onClick = { product ->
                                    navController.navigate(
                                        ProductDetailRoute(
                                            UiProductModel.fromProduct(
                                                product
                                            )
                                        )
                                    )
                                })
                                shouldShowBottomBar.value = true
                            }
                            composable<CartScreen> {
                                CartListScreen(onCheckOutClick = {
                                    navController.navigate(CartSummaryRoute)
                                    shouldShowBottomBar.value = false
                                })
                            }

                            composable<ProfileScreen> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Welcome to profile!")
                                }
                                shouldShowBottomBar.value = true
                            }
                            composable<ProductDetailRoute>(
                                typeMap = mapOf(typeOf<UiProductModel>() to productNavType)
                            ) {
                                val arguments = it.toRoute<ProductDetailRoute>()
                                ProductDetailScreen(arguments.product)
                                shouldShowBottomBar.value = false
                            }
                            composable<CartSummaryRoute> {
                                CartSummaryScreen()
                                shouldShowBottomBar.value = false
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val navItems = listOf(
            BottomNavItem.Home,
            BottomNavItem.Cart,
            BottomNavItem.Profile
        )
        navItems.forEach { item ->
            val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { starRoute ->
                            popUpTo(starRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                    )
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}


sealed class BottomNavItem(val route: Any, val title: String, val icon: Int) {
    object Home : BottomNavItem(HomeScreen, "Home", R.drawable.ic_home)
    object Cart : BottomNavItem(CartScreen, "Cart", R.drawable.ic_cart)
    object Profile : BottomNavItem(ProfileScreen, "Profile", R.drawable.ic_profile_img)
}
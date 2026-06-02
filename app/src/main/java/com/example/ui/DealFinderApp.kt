package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.ShoppingListRepository
import com.example.ui.compare.CompareScreen
import com.example.ui.flyers.FlyersScreen
import com.example.ui.home.HomeScreen
import com.example.ui.lists.ListScreen
import com.example.ui.lists.ListViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import com.example.ui.account.AccountScreen
import com.example.ui.coupons.CouponsScreen
import com.example.ui.notifications.NotificationsScreen
import com.example.ui.location.LocationScreen

import com.example.ui.product.ProductDetailScreen
import com.example.ui.flyers.FlyerViewerScreen
import com.example.ui.retailer.RetailerScreen

@Composable
fun DealFinderApp(repository: ShoppingListRepository) {
    val navController = rememberNavController()
    
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Lists,
        Screen.Deals,
        Screen.Account
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        val listViewModel = remember { ListViewModel(repository) }
        
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToCompare = { navController.navigate(Screen.Search.route) },
                    onNavigateToFlyers = { navController.navigate(Screen.Deals.route) },
                    onNavigateToList = { navController.navigate(Screen.Lists.route) },
                    onNavigateToCoupons = { navController.navigate(Screen.Coupons.route) },
                    onNavigateToLocation = { navController.navigate(Screen.Location.route) },
                    onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                    onNavigateToFlyer = { flyerId -> navController.navigate(Screen.FlyerViewer.createRoute(flyerId)) },
                    onNavigateToProduct = { productId -> navController.navigate(Screen.ProductDetail.createRoute(productId)) }
                )
            }
            composable(Screen.Deals.route) { 
                FlyersScreen(
                    onNavigateToFlyer = { flyerId -> navController.navigate(Screen.FlyerViewer.createRoute(flyerId)) },
                    onNavigateToProduct = { productId -> navController.navigate(Screen.ProductDetail.createRoute(productId)) }
                ) 
            } 
            
            composable(Screen.Search.route) { // Map Compare logic to Search for now
                CompareScreen(
                    onAddToList = { product ->
                        listViewModel.addItem(
                            com.example.data.ShoppingListItem(
                                productId = product.id,
                                name = product.name,
                                targetPrice = product.currentPrice
                            )
                        )
                    },
                    onNavigateToProduct = { productId -> navController.navigate(Screen.ProductDetail.createRoute(productId)) }
                ) 
            }
            composable(Screen.Lists.route) {
                ListScreen(viewModel = listViewModel)
            }
            composable(Screen.Account.route) {
                AccountScreen()
            }
            composable(Screen.Coupons.route) {
                CouponsScreen()
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.Location.route) {
                LocationScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.ProductDetail.route) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                ProductDetailScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() },
                    onNavigateToProduct = { newProductId -> navController.navigate(Screen.ProductDetail.createRoute(newProductId)) },
                    onNavigateToRetailer = { storeId -> navController.navigate(Screen.Retailer.createRoute(storeId)) },
                    onAddToList = {
                        val product = com.example.data.MockData.products.find { it.id == productId }
                        if (product != null) {
                            listViewModel.addItem(
                                com.example.data.ShoppingListItem(
                                    productId = product.id,
                                    name = product.name,
                                    targetPrice = product.currentPrice
                                )
                            )
                        }
                    }
                )
            }
            composable(Screen.FlyerViewer.route) { backStackEntry ->
                val flyerId = backStackEntry.arguments?.getString("flyerId") ?: return@composable
                FlyerViewerScreen(
                    flyerId = flyerId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Retailer.route) { backStackEntry ->
                val storeId = backStackEntry.arguments?.getString("storeId") ?: return@composable
                RetailerScreen(
                    storeId = storeId,
                    onBack = { navController.popBackStack() },
                    onNavigateToProduct = { productId -> navController.navigate(Screen.ProductDetail.createRoute(productId)) }
                )
            }
        }
    }
}

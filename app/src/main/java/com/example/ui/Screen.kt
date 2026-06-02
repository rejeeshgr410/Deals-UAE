package com.example.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector, val activeIcon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    object Search : Screen("search", "Search", Icons.Outlined.Search, Icons.Filled.Search)
    object Lists : Screen("lists", "Lists", Icons.AutoMirrored.Outlined.List, Icons.AutoMirrored.Filled.List)
    object Deals : Screen("deals", "Deals", Icons.Outlined.LocalOffer, Icons.Filled.LocalOffer)
    object Account : Screen("account", "Account", Icons.Outlined.Person, Icons.Filled.Person)
    object Coupons : Screen("coupons", "Coupons", Icons.Outlined.LocalOffer, Icons.Filled.LocalOffer)
    object Notifications : Screen("notifications", "Notifications", Icons.Outlined.Notifications, Icons.Filled.Notifications)
    object Location : Screen("location", "Location", Icons.Outlined.LocationOn, Icons.Filled.LocationOn)
    object ProductDetail : Screen("product_detail/{productId}", "Product details", Icons.Outlined.Info, Icons.Filled.Info) {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object FlyerViewer : Screen("flyer_viewer/{flyerId}", "Flyer viewer", Icons.Outlined.Info, Icons.Filled.Info) {
        fun createRoute(flyerId: String) = "flyer_viewer/$flyerId"
    }
    object Retailer : Screen("retailer/{storeId}", "Retailer details", Icons.Outlined.Info, Icons.Filled.Info) {
        fun createRoute(storeId: String) = "retailer/$storeId"
    }
}

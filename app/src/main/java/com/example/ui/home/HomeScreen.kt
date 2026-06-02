package com.example.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.CompareArrows
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.Flyer
import com.example.data.MockData
import com.example.data.Product
import com.example.data.Store
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCompare: () -> Unit,
    onNavigateToFlyers: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToCoupons: () -> Unit,
    onNavigateToLocation: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToFlyer: (String) -> Unit,
    onNavigateToProduct: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { HomeHeader(onNavigateToCompare = onNavigateToCompare, onNavigateToLocation = onNavigateToLocation, onNavigateToNotifications = onNavigateToNotifications) }
            item { 
                QuickActions(
                    onNavigateToCompare = onNavigateToCompare,
                    onNavigateToFlyers = onNavigateToFlyers,
                    onNavigateToList = onNavigateToList,
                    onNavigateToCoupons = onNavigateToCoupons
                ) 
            }
            
            item {
                SectionHeader(title = "Nearby Flyers", subtitle = "Within 10 km", action = "View all", onAction = onNavigateToFlyers)
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(MockData.flyers) { flyer -> FlyerCard(flyer, onClick = { onNavigateToFlyer(flyer.id) }) }
                }
            }
            
            item {
                SectionHeader(title = "Top Deals Today", action = "View all", onAction = onNavigateToCompare)
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(MockData.products) { product -> ProductCard(product, onClick = { onNavigateToProduct(product.id) }) }
                }
            }

            item {
                SmartSavingsBanner(onAction = onNavigateToCompare)
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(onNavigateToCompare: () -> Unit, onNavigateToLocation: () -> Unit, onNavigateToNotifications: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hi, Hassan \uD83D\uDC4B",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Find the best deals around you",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    onClick = onNavigateToLocation
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Dubai", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
                BadgedBox(
                    badge = { Badge(modifier = Modifier.offset(x = (-6).dp, y = 6.dp), containerColor = Red600) { } }
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.size(40.dp),
                        onClick = onNavigateToNotifications
                    ) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shadowElevation = 0.dp,
            onClick = onNavigateToCompare
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Search products, brands or stores",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = CircleShape,
                    color = GoldBackground,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = null, tint = Color.Black, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

data class QuickAction(val name: String, val icon: ImageVector, val color: Color, val iconColor: Color, val onClick: () -> Unit)

@Composable
fun QuickActions(
    onNavigateToCompare: () -> Unit,
    onNavigateToFlyers: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToCoupons: () -> Unit
) {
    val items = listOf(
        QuickAction("Nearby Deals", Icons.Outlined.LocationOn, CatGreen, CatGreenIcon, onNavigateToFlyers),
        QuickAction("All Flyers", Icons.Outlined.LocalOffer, CatRed, CatRedIcon, onNavigateToFlyers),
        QuickAction("Price Compare", Icons.Outlined.CompareArrows, CatPurple, CatPurpleIcon, onNavigateToCompare),
        QuickAction("Smart List", Icons.AutoMirrored.Outlined.List, CatYellow, CatYellowIcon, onNavigateToList),
        QuickAction("Coupons", Icons.Outlined.ConfirmationNumber, CatPink, CatPinkIcon, onNavigateToCoupons)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(items) { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 8.dp)) {
                Surface(
                    shape = CircleShape,
                    color = item.color,
                    modifier = Modifier.size(60.dp),
                    onClick = item.onClick
                ) {
                    Icon(item.icon, contentDescription = item.name, modifier = Modifier.padding(16.dp), tint = item.iconColor)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.name, style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp), fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String? = null, action: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            if (subtitle != null) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(Icons.Outlined.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = Purple40)
                Spacer(modifier = Modifier.width(4.dp))
                Text(subtitle, style = MaterialTheme.typography.labelMedium, color = Purple40)
            }
        }
        TextButton(onClick = onAction) {
            Text(action, style = MaterialTheme.typography.labelMedium, color = Purple40)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlyerCard(flyer: Flyer, onClick: () -> Unit = {}) {
    val store = MockData.getStore(flyer.storeId)
    // Custom soft pastel backgrounds based on index
    val bgColor = when(kotlin.math.abs(flyer.id.hashCode()) % 4) {
        0 -> Color(0xFFF1F8E9) // Light Greenish
        1 -> Color(0xFFE3F2FD) // Light Blue
        2 -> Color(0xFFFFF3E0) // Light Orange
        else -> Color(0xFFFCE4EC) // Light Pink
    }
    Card(
        onClick = onClick,
        modifier = Modifier.width(160.dp).height(210.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {
                AsyncImage(
                    model = store?.logoUrl,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp).fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(store?.name ?: flyer.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Valid till 14 May", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                color = Color(0xFFFFEBEE),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "${flyer.expiryDays} DAYS LEFT",
                    color = Color(0xFFE53935), // Pure red for text
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(product: Product, store: Store? = null, onClick: () -> Unit = {}, onAddToList: (() -> Unit)? = null) {
    val displayStore = store ?: MockData.getStore(product.storeId)
    Card(
        onClick = onClick,
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                ) {
                    Text(
                        "-20%",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = product.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(text = product.brand, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "AED ${product.currentPrice}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (product.oldPrice != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "AED ${product.oldPrice}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.outline,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = displayStore?.name ?: "", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            
            if (onAddToList != null) {
                Spacer(modifier = Modifier.height(8.dp))
                FilledTonalButton(
                    onClick = onAddToList,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add to List")
                }
            }
        }
    }
}

@Composable
fun SmartSavingsBanner(onAction: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        color = LightPeriwinkle
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Smart Savings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Compare prices, build your list and save more every day.", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onAction,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D2A4A)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Create Smart List", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                }
            }
            // Would be an illustration here
            Box(modifier = Modifier.size(80.dp).background(Color(0xFFB0A4F6), CircleShape)) {
                 Icon(Icons.Default.ShoppingBasket, contentDescription = null, modifier = Modifier.fillMaxSize().padding(16.dp), tint = Color.White)
            }
        }
    }
}

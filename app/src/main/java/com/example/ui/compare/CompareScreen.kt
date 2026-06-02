package com.example.ui.compare

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MockData
import com.example.data.Product
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(onAddToList: (Product) -> Unit, onNavigateToProduct: (String) -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredProducts = if (searchQuery.isBlank()) {
        MockData.products
    } else {
        MockData.products.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SearchHeader() }
            item {
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            Text("rice 5kg", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
                        }
                        Icon(Icons.Outlined.Cancel, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(GoldBackground, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.FilterList, contentDescription = null, tint = Color.Black)
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("Popular Searches", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    val queries = listOf(
                        Pair("Milk", Color(0xFFE3F2FD)), Pair("Chicken", Color(0xFFFFE0B2)), 
                        Pair("Detergent", Color(0xFFEDE7F6)), Pair("Banana", Color(0xFFFFF9C4)), 
                        Pair("Eggs", Color(0xFFE8F5E9))
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(queries) { q ->
                            Surface(
                                shape = RoundedCornerShape(24.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                                     Surface(shape = RoundedCornerShape(8.dp), color = q.second, modifier = Modifier.size(20.dp)) {
                                         // placeholder icon
                                         Icon(Icons.Outlined.EmojiObjects, contentDescription = null, modifier = Modifier.padding(4.dp), tint = Color.DarkGray)
                                     }
                                     Spacer(modifier = Modifier.width(8.dp))
                                     Text(q.first, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Results for \"Milk\"", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sort by", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Relevance", style = MaterialTheme.typography.labelMedium, color = Purple40, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp), tint = Purple40)
                    }
                }
            }

            items(filteredProducts) { product ->
                SearchResultItem(
                    product = product,
                    isCheapest = product.id == "1",
                    onAddToList = { onAddToList(product) },
                    onClick = { onNavigateToProduct(product.id) }
                )
            }
            
            item { MissingItemBanner() }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun MissingItemBanner() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
        shape = RoundedCornerShape(24.dp),
        color = LightPeriwinkle
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
             Surface(shape = CircleShape, color = Color.White, modifier = Modifier.size(48.dp)) {
                  Icon(Icons.Outlined.LocalOffer, contentDescription = null, modifier = Modifier.padding(12.dp), tint = Purple40)
             }
             Spacer(modifier = Modifier.width(16.dp))
             Column(modifier = Modifier.weight(1f)) {
                 Text("Can't find what you're looking for?", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                 Spacer(modifier = Modifier.height(4.dp))
                 Text("Create a price alert and we'll notify you when a deal is available.", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
             }
             Spacer(modifier = Modifier.width(16.dp))
             Button(
                 onClick = { },
                 colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                 shape = RoundedCornerShape(20.dp),
             ) {
                 Text("Create Alert", color = Color.White)
                 Spacer(modifier = Modifier.width(8.dp))
                 Icon(Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.size(16.dp))
             }
        }
    }
}

@Composable
fun SearchHeader() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Find products across all stores",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Dubai", style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
                BadgedBox(
                    badge = { Badge(modifier = Modifier.offset(x = (-6).dp, y = 6.dp), containerColor = Color(0xFFE53935)) { } }
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultItem(product: Product, isCheapest: Boolean = false, onClick: () -> Unit = {}, onAddToList: () -> Unit) {
    val store = MockData.getStore(product.storeId)
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                         Text(product.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, maxLines = 2)
                         Spacer(modifier = Modifier.height(4.dp))
                         Text(product.brand, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    if (isCheapest) {
                        Surface(color = CatGreen, shape = RoundedCornerShape(8.dp)) {
                            Text("Cheapest", color = Teal500, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Column {
                         Surface(color = CatGreen, shape = RoundedCornerShape(8.dp)) {
                             Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                 Text("4.6", style = MaterialTheme.typography.labelSmall, color = Teal500, fontWeight = FontWeight.Bold)
                                 Icon(Icons.Default.Star, contentDescription = null, tint = Teal500, modifier = Modifier.size(10.dp).padding(start = 2.dp))
                             }
                         }
                         Spacer(modifier = Modifier.height(8.dp))
                         Text("1kg = AED 6.60", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                         Text("AED ${product.currentPrice}", fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleLarge, color = Color(0xFFD32F2F))
                         Row(verticalAlignment = Alignment.CenterVertically) {
                             if (product.oldPrice != null) {
                                 Text(
                                     "AED ${product.oldPrice}",
                                     style = MaterialTheme.typography.labelMedium,
                                     color = MaterialTheme.colorScheme.outline,
                                     textDecoration = TextDecoration.LineThrough
                                 )
                                 Spacer(modifier = Modifier.width(6.dp))
                                 Surface(color = CatRed, shape = RoundedCornerShape(6.dp)) {
                                     Text("-21%", color = CatRedIcon, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp), fontWeight = FontWeight.Bold)
                                 }
                             }
                         }
                         Spacer(modifier = Modifier.height(12.dp))
                         Row(verticalAlignment = Alignment.CenterVertically) {
                             AsyncImage(model = store?.logoUrl, contentDescription = null, modifier = Modifier.size(20.dp).clip(CircleShape))
                             Spacer(modifier = Modifier.width(8.dp))
                             Column(horizontalAlignment = Alignment.End) {
                                 Text(store?.name ?: "", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                                 Text("1.2 km away", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.outline)
                             }
                         }
                    }
                }
            }
        }
    }
}

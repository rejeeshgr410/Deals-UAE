package com.example.ui.lists

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.data.ShoppingListItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: ListViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightPeriwinkle),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Outlined.Share, contentDescription = null, tint = Purple40)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Share List", color = Purple40, style = MaterialTheme.typography.titleMedium)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1.2f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldBackground),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Outlined.Assessment, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Compare Basket Prices", color = Color.Black, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { ListHeader() }
            item { ListTabs() }
            item { BudgetCard() }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Current List", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("${if (uiState.isEmpty()) MockData.products.size else uiState.size} items", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, contentDescription = null) }
                    }
                }
            }

            // Using MockData if empty list just to display the UI properly for screenshots
            val displayItems = if (uiState.isEmpty()) MockData.products else emptyList() // Fallback to mock for UI look
            if (displayItems.isNotEmpty()) {
                items(displayItems) { product ->
                    ListItemView(
                        product = product,
                        selected = product.id.hashCode() % 2 == 0
                    )
                }
            } else {
                items(uiState) { shopItem ->
                    val product = MockData.products.find { it.id == shopItem.productId }
                    if (product != null) {
                        ListItemView(product = product, selected = true)
                    }
                }
            }

            item { BestBasketPrice() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun ListHeader() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "My Lists",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Plan, track and save on your shopping",
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

@Composable
fun ListTabs() {
    val tabs = listOf(
        Pair("My Lists", Icons.Outlined.ShoppingBasket),
        Pair("Grocery", Icons.Outlined.ShoppingCart),
        Pair("Ramadan", Icons.Outlined.NightsStay),
        Pair("Party", Icons.Outlined.Celebration),
        Pair("New List", Icons.Default.Add)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tabs.size) { i ->
            val tab = tabs[i]
            val isFirst = i == 0
            val isLast = i == tabs.size - 1
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isFirst) GoldBackground else if (isLast) LightPeriwinkle else MaterialTheme.colorScheme.surface,
                border = if (!isFirst && !isLast) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        tab.second,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (isFirst) Color.Black else if (isLast) Purple40 else Teal500
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        tab.first,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isFirst) Color.Black else if (isLast) Purple40 else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun BudgetCard() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF3E5F5)
    ) {
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Monthly Budget", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("AED", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("500.00", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp).padding(bottom = 4.dp), tint = Color(0xFF7E57C2))
                }
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                    color = Color(0xFF7E57C2),
                    trackColor = Color(0xFFD1C4E9)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("AED 265.50 spent", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("AED 234.50 left", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Divider(modifier = Modifier.height(100.dp).width(1.dp), color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(100.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("You'll Save", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.outline)
                }
                Text("AED 42.30", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Teal500)
                Text("with best prices", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                // Box acting as an illustration placeholder
                Box(modifier = Modifier.size(60.dp).background(Color(0xFFB0A4F6), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.ShoppingBasket, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}

@Composable
fun ListItemView(product: com.example.data.Product, selected: Boolean) {
    val store = MockData.getStore(product.storeId)
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).background(Color.White, RoundedCornerShape(16.dp)).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (selected) Purple40 else Color.Transparent,
            border = if (!selected) BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant) else null,
            modifier = Modifier.size(28.dp)
        ) {
            if (selected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.padding(4.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        AsyncImage(
            model = product.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text("${1} × 5kg", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Column(horizontalAlignment = Alignment.End) {
            Surface(
                color = CatGreen,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(store?.name ?: "", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = Teal500, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("AED ${product.currentPrice}", fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleMedium)
                if (product.oldPrice != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "AED ${product.oldPrice}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun BestBasketPrice() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF1FAEE)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFC8E6C9),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.LocalOffer, contentDescription = null, modifier = Modifier.padding(12.dp), tint = Color(0xFF2E7D32))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Best Basket Price", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline)
                }
                Text("At Nesto Hypermarket", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("8 items", color = Color(0xFF2E7D32), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("AED 87.40", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge, color = Color(0xFF2E7D32))
                Text("Original: AED 129.70", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline, textDecoration = TextDecoration.LineThrough)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("You save AED 42.30", color = Color(0xFF2E7D32), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
            }
        }
    }
}

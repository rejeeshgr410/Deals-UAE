package com.example.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.MockData
import com.example.ui.home.SectionHeader
import com.example.ui.compare.SearchResultItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: String, onBack: () -> Unit, onAddToList: () -> Unit, onNavigateToProduct: (String) -> Unit = {}, onNavigateToRetailer: (String) -> Unit = {}) {
    val product = MockData.products.find { it.id == productId } ?: return
    val store = MockData.getStore(product.storeId) ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Save */ }) {
                        Icon(Icons.Outlined.FavoriteBorder, "Save Deal")
                    }
                    IconButton(onClick = { /* Alert */ }) {
                        Icon(Icons.Outlined.Notifications, "Price Alert")
                    }
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, "Share")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.brand,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "AED ${"%.2f".format(product.currentPrice)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (product.oldPrice != null) {
                        Text(
                            text = "AED ${"%.2f".format(product.oldPrice)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    onClick = { onNavigateToRetailer(store.id) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = store.logoUrl,
                            contentDescription = store.name,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Available at", style = MaterialTheme.typography.labelMedium)
                            Text(store.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onAddToList,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Add to List")
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                SectionHeader(title = "Price Comparison", action = "", onAction = {})
                
                // Compare feature: Find all products with the same name across stores
                val similarProducts = MockData.products.filter { it.name == product.name }.sortedBy { it.currentPrice }
                similarProducts.forEach { simProduct ->
                    SearchResultItem(
                        product = simProduct,
                        isCheapest = simProduct.id == similarProducts.first().id,
                        onClick = { onNavigateToProduct(simProduct.id) },
                        onAddToList = onAddToList
                    )
                }
            }
        }
    }
}

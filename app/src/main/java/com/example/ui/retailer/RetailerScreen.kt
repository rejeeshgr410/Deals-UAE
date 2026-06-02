package com.example.ui.retailer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.MockData
import com.example.ui.compare.SearchResultItem
import com.example.ui.home.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetailerScreen(storeId: String, onBack: () -> Unit, onNavigateToProduct: (String) -> Unit) {
    val store = MockData.getStore(storeId) ?: return
    val storeProducts = MockData.products.filter { it.storeId == storeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(store.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Store Info */ }) {
                        Icon(Icons.Default.Info, "Store Info")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = store.logoUrl,
                        contentDescription = store.name,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(store.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text("${store.branches.size} Branches nearby", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            item {
                SectionHeader(title = "Top Deals at ${store.name}", action = "", onAction = {})
            }

            items(storeProducts) { product ->
                SearchResultItem(
                    product = product,
                    isCheapest = true,
                    onClick = { onNavigateToProduct(product.id) },
                    onAddToList = {}
                )
            }
        }
    }
}

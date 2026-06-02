package com.example.ui.coupons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CatPink
import com.example.ui.theme.CatPinkIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Coupons") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = CatPink,
                modifier = Modifier.size(120.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.ConfirmationNumber, 
                        contentDescription = null, 
                        modifier = Modifier.size(60.dp),
                        tint = CatPinkIcon
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("No active coupons", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Save coupons here to scan them at checkout.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

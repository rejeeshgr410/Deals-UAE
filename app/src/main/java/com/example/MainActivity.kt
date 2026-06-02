package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.ShoppingListRepository
import com.example.ui.DealFinderApp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "shopping-db"
    ).fallbackToDestructiveMigration().build()
    val repository = ShoppingListRepository(db.shoppingListDao())

    setContent {
      MyApplicationTheme {
        DealFinderApp(repository)
      }
    }
  }
}



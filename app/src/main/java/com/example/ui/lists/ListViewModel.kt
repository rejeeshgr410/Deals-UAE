package com.example.ui.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ShoppingListItem
import com.example.data.ShoppingListRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ShoppingListRepository) : ViewModel() {
    val uiState: StateFlow<List<ShoppingListItem>> = repository.allItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeItem(id: String) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    fun addItem(item: ShoppingListItem) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }
}

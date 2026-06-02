package com.example.data

import kotlinx.coroutines.flow.Flow

class ShoppingListRepository(private val dao: ShoppingListDao) {
    val allItems: Flow<List<ShoppingListItem>> = dao.getAllItems()

    suspend fun insert(item: ShoppingListItem) = dao.insertItem(item)
    
    suspend fun delete(id: String) = dao.deleteItem(id)
}

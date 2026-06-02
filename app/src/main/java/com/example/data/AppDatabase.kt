package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "shopping_list")
data class ShoppingListItem(
    @PrimaryKey val productId: String,
    val name: String,
    val targetPrice: Double,
    val qty: Int = 1
)

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list")
    fun getAllItems(): Flow<List<ShoppingListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingListItem)
    
    @Query("DELETE FROM shopping_list WHERE productId = :id")
    suspend fun deleteItem(id: String)
}

@Dao
interface DealFinderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStores(stores: List<StoreEntity>)

    @Query("SELECT * FROM stores")
    fun getStores(): Flow<List<StoreEntity>>
    
    @Query("SELECT * FROM stores WHERE id = :storeId")
    suspend fun getStoreById(storeId: String): StoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlyers(flyers: List<FlyerEntity>)

    @Query("SELECT * FROM flyers")
    fun getFlyers(): Flow<List<FlyerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<ProductEntity>>
}

@Database(entities = [
    ShoppingListItem::class,
    UserEntity::class,
    StoreEntity::class,
    FlyerEntity::class,
    ProductEntity::class,
    PriceEntity::class,
    AlertEntity::class,
    DealEntity::class,
    CouponEntity::class
], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun dealFinderDao(): DealFinderDao
}

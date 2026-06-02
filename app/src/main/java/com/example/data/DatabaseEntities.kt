package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val isGuest: Boolean
)

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey val id: String,
    val name: String,
    val logoUrl: String,
    val branchesStr: String // comma separated
)

@Entity(tableName = "flyers")
data class FlyerEntity(
    @PrimaryKey val id: String,
    val storeId: String,
    val title: String,
    val imageUrl: String,
    val expiryDays: Int
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val currentPrice: Double,
    val oldPrice: Double?, // Room supports nullable
    val storeId: String,
    val imageUrl: String
)

@Entity(tableName = "prices")
data class PriceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val storeId: String,
    val price: Double,
    val timestamp: Long
)

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val targetPrice: Double,
    val isActive: Boolean
)

@Entity(tableName = "deals")
data class DealEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val expiry: Long
)

@Entity(tableName = "coupons")
data class CouponEntity(
    @PrimaryKey val id: String,
    val title: String,
    val code: String,
    val discountInfo: String,
    val expiry: Long
)

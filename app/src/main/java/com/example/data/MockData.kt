package com.example.data

data class Store(
    val id: String,
    val name: String,
    val logoUrl: String,
    val branches: List<String> = listOf("Dubai", "Abu Dhabi", "Sharjah")
)

data class Flyer(
    val id: String,
    val storeId: String,
    val title: String,
    val imageUrl: String,
    val expiryDays: Int
)

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val currentPrice: Double,
    val oldPrice: Double?,
    val storeId: String,
    val imageUrl: String
)

object MockData {
    val stores = listOf(
        Store("1", "Carrefour", "https://picsum.photos/seed/carrefour/200/200"),
        Store("2", "Lulu Hypermarket", "https://picsum.photos/seed/lulu/200/200"),
        Store("3", "Nesto", "https://picsum.photos/seed/nesto/200/200"),
        Store("4", "Union Coop", "https://picsum.photos/seed/union/200/200"),
        Store("5", "Viva", "https://picsum.photos/seed/viva/200/200")
    )

    val flyers = listOf(
        Flyer("1", "1", "Weekend Super Sale", "https://picsum.photos/seed/f1/400/600", 2),
        Flyer("2", "2", "Electronics Mega Offers", "https://picsum.photos/seed/f2/400/600", 5),
        Flyer("3", "3", "Half Price Deals", "https://picsum.photos/seed/f3/400/600", 1),
        Flyer("4", "4", "Fresh Produce Market", "https://picsum.photos/seed/f4/400/600", 7),
        Flyer("5", "5", "Essentials Discount", "https://picsum.photos/seed/f5/400/600", 3)
    )

    val products = listOf(
        Product("1", "Basmati Rice 5kg", "India Gate", 24.50, 35.00, "1", "https://picsum.photos/seed/rice/300/300"),
        Product("2", "Sunflower Oil 1.5L", "Noor", 12.99, 18.50, "1", "https://picsum.photos/seed/oil/300/300"),
        Product("3", "Coca Cola 330ml x 6", "Coca Cola", 10.50, 15.00, "2", "https://picsum.photos/seed/cola/300/300"),
        Product("4", "Fresh Milk 2L", "Al Ain", 11.00, null, "3", "https://picsum.photos/seed/milk/300/300"),
        Product("5", "Pampers Size 4", "Pampers", 45.00, 60.00, "4", "https://picsum.photos/seed/diaper/300/300"),
        Product("6", "Basmati Rice 5kg", "India Gate", 22.00, 30.00, "2", "https://picsum.photos/seed/rice/300/300"),
        Product("7", "Coca Cola 330ml x 6", "Coca Cola", 9.99, 14.00, "5", "https://picsum.photos/seed/cola/300/300")
    )

    fun getStore(id: String): Store? = stores.find { it.id == id }
}

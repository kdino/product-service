package ki.product.model

import ki.product.common.Utils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Product(
    val id: String,
    val price: Int,
    val brand: Brand,
    val category: Category,
    val created: Instant,
    val modified: Instant? = null,
) {
    companion object {
        fun create(price: Int, brand: Brand, category: Category) =
            Product(
                id = Utils.generateId("product"),
                price = price,
                brand = brand,
                category = category,
                created = Clock.System.now(),
            )
    }

    fun update(price: Int?, category: Category?, brand: Brand?) =
        copy(
            price = price ?: this.price,
            category = category ?: this.category,
            brand = brand ?: this.brand,
        )

    data class CreateCommand(
        val price: Int,
        val brandId: String,
        val category: Category,
    )

    data class UpdateCommand(
        val price: Int?,
        val brandId: String?,
        val category: Category?,
    )

    enum class Category {
        TOP,
        OUTER,
        PANTS,
        SNEAKERS,
        BAG,
        CAP,
        SOCKS,
        ACCESSORY,
    }
}

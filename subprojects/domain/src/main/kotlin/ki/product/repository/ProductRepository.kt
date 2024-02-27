package ki.product.repository

import arrow.core.raise.Effect
import ki.product.model.CategoryItem
import ki.product.model.Product

interface ProductRepository {
    fun getCheapestItemByCategory(category: Product.Category): Effect<Failure, CategoryItem>
    fun getCheapestBrand(): Effect<Failure, Product>

    sealed class Failure(
        override val message: String?,
        override val cause: Throwable?,
    ) : Throwable(message, cause) {
        data class DbError(
            override val message: String? = null,
            override val cause: Throwable? = null,
        ) : Failure(message, cause)

        data class NoData(
            override val message: String? = null,
            override val cause: Throwable? = null,
        ) : Failure(message, cause)
    }
}

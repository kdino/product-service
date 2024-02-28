package ki.product.repository

import arrow.core.raise.Effect
import ki.product.model.CategoryItem
import ki.product.model.Product

interface ProductRepository {
    fun getCheapestItemByCategory(category: Product.Category): Effect<ReadFailure, CategoryItem>
    fun getMostExpensiveItemByCategory(category: Product.Category): Effect<ReadFailure, CategoryItem>
    fun getCheapestBrand(): Effect<ReadFailure, Product>
    fun getProductByBrandName(brandName: String): Effect<Failure, Product?>
    fun createProduct(product: Product): Effect<Failure, Product>
    fun getProduct(brandName: String): Effect<ReadFailure, Product>
    fun updateProduct(product: Product): Effect<Failure, Product>
    fun deleteProduct(brandName: String): Effect<ReadFailure, Unit>

    sealed class ReadFailure(
        override val message: String?,
        override val cause: Throwable?,
    ) : Throwable(message, cause) {
        data class DbError(
            override val message: String? = null,
            override val cause: Throwable? = null,
        ) : ReadFailure(message, cause)

        data class NoData(
            override val message: String? = null,
            override val cause: Throwable? = null,
        ) : ReadFailure(message, cause)
    }

    sealed class Failure(
        override val message: String?,
        override val cause: Throwable?,
    ) : Throwable(message, cause) {
        data class DbError(
            override val message: String? = null,
            override val cause: Throwable? = null,
        ) : Failure(message, cause)
    }
}

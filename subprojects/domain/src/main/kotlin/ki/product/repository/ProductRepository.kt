package ki.product.repository

import arrow.core.raise.Effect
import ki.product.model.Product
import ki.product.model.ProductSummary

interface ProductRepository {
    fun getCheapestBrandByCategory(category: Product.Category): Effect<Failure, ProductSummary>

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

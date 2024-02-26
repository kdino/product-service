package ki.product.service

import arrow.core.raise.Effect

interface ProductService {
    fun getCheapestCombination(): Effect<Failure, CheapestCombinationResult>

    sealed class Failure(
        override val message: String?,
    ) : Throwable(message) {
        data class InternalServerError(
            override val message: String?,
        ) : Failure(message)
    }

    data class CheapestCombinationResult(
        val top: ProductSummary,
        val outer: ProductSummary,
        val pants: ProductSummary,
        val sneakers: ProductSummary,
        val bag: ProductSummary,
        val cap: ProductSummary,
        val socks: ProductSummary,
        val accessory: ProductSummary,
    ) {
        data class ProductSummary(
            val brandName: String,
            val price: Int,
        )
    }
}

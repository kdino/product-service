package ki.product.service

import arrow.core.raise.Effect
import ki.product.model.BrandItem
import ki.product.model.CategoryItem

interface ProductService {
    fun getCheapestCombination(): Effect<Failure, CheapestCombinationResult>
    fun getCheapestBrand(): Effect<Failure, CheapestBrandResult>

    sealed class Failure(
        override val message: String?,
    ) : Throwable(message) {
        data class DataNotFound(
            override val message: String?,
        ) : Failure(message)
        data class InternalServerError(
            override val message: String?,
        ) : Failure(message)
    }

    data class CheapestCombinationResult(
        val top: CategoryItem,
        val outer: CategoryItem,
        val pants: CategoryItem,
        val sneakers: CategoryItem,
        val bag: CategoryItem,
        val cap: CategoryItem,
        val socks: CategoryItem,
        val accessory: CategoryItem,
        val total: Int,
    )

    data class CheapestBrandResult(
        val brandName: String,
        val brandItems: List<BrandItem>,
        val total: Int
    )
}

package ki.product.service

import arrow.core.raise.Effect
import ki.product.model.BrandItem
import ki.product.model.CategoryItem
import ki.product.model.Product

interface ProductService {
    fun getCheapestCombination(): Effect<Failure, CheapestCombinationResult>
    fun getCheapestBrand(): Effect<Failure, CheapestBrandResult>
    fun getCategorySummary(category: Product.Category): Effect<GetCategorySummaryFailure, CategorySummaryResult>

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

    sealed class GetCategorySummaryFailure(
        override val message: String?,
    ) : Throwable(message) {
        data class DataNotFound(
            override val message: String?,
        ) : GetCategorySummaryFailure(message)
        data class InternalServerError(
            override val message: String?,
        ) : GetCategorySummaryFailure(message)
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
        val total: Int,
    )

    data class CategorySummaryResult(
        val category: Product.Category,
        val cheapest: CategoryItem,
        val mostExpensive: CategoryItem,
    )
}

package ki.product.service

import arrow.core.raise.Effect
import ki.product.model.CategoryItem
import ki.product.model.Product

interface ProductService {
    fun getCheapestCombination(): Effect<GetProductFailure, CheapestCombinationResult>
    fun getCheapestBrand(): Effect<GetCheapestBrandFailure, CheapestBrandResult>
    fun getCategorySummary(category: Product.Category): Effect<GetProductFailure, CategorySummaryResult>
    fun createProduct(command: Product.CreateCommand): Effect<CreateProductFailure, Product>
    fun getProduct(id: String): Effect<GetProductFailure, Product>
    fun updateProduct(
        id: String,
        command: Product.UpdateCommand,
    ): Effect<UpdateProductFailure, Product>

    fun deleteProduct(id: String): Effect<DeleteProductFailure, Unit>

    sealed interface GetProductFailure
    sealed interface GetCheapestBrandFailure
    sealed interface CreateProductFailure
    sealed interface UpdateProductFailure
    sealed interface DeleteProductFailure

    data object BrandNotFound : GetCheapestBrandFailure, CreateProductFailure, UpdateProductFailure
    data object ProductNotFound : GetProductFailure, GetCheapestBrandFailure, UpdateProductFailure, DeleteProductFailure
    data class InternalError(val message: String?) :
        GetProductFailure,
        GetCheapestBrandFailure,
        CreateProductFailure,
        UpdateProductFailure,
        DeleteProductFailure

    data class CheapestCombinationResult(
        val categories: Map<Product.Category, CategoryItem>,
        val total: Int,
    )

    data class CheapestBrandResult(
        val brandName: String,
        val categories: Map<Product.Category, Product>,
        val total: Int,
    )

    data class CategorySummaryResult(
        val category: Product.Category,
        val cheapest: CategoryItem,
        val mostExpensive: CategoryItem,
    )
}

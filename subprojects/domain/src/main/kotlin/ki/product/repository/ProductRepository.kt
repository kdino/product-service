package ki.product.repository

import arrow.core.raise.Effect
import ki.product.model.CategoryItem
import ki.product.model.Product

interface ProductRepository {
    fun getCheapestItemByCategory(category: Product.Category): Effect<DbError, CategoryItem>
    fun getMostExpensiveItemByCategory(category: Product.Category): Effect<DbError, CategoryItem>
    fun getProductsByBrandId(brandId: String): Effect<DbError, List<Product>>

    fun create(product: Product): Effect<DbError, Product>
    fun get(id: String): Effect<DbError, Product?>
    fun update(product: Product): Effect<DbError, Product>
    fun delete(id: String): Effect<DbError, Unit>

    // 브랜드 추가를 위한 함수
    fun bulkCreate(products: List<Product>): Effect<DbError, Unit>

    data class DbError(val message: String?)
}

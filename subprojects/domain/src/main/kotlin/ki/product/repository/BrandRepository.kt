package ki.product.repository

import arrow.core.raise.Effect
import ki.product.model.Brand

interface BrandRepository {
    suspend fun getById(id: String): Effect<DbError, Brand?>
    suspend fun getByName(name: String): Effect<DbError, Brand?>
    suspend fun getAll(): Effect<DbError, List<Brand>>
    suspend fun create(brand: Brand): Effect<DbError, Brand>
    suspend fun update(brand: Brand): Effect<DbError, Brand>
    suspend fun delete(id: String): Effect<DbError, Unit>

    data class DbError(val message: String?)
}

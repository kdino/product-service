package ki.product.repository

import arrow.core.raise.Effect
import arrow.core.raise.effect
import ki.product.config.DatabaseFactory
import ki.product.entity.Brands
import ki.product.entity.Products
import ki.product.model.Brand
import ki.product.model.CategoryItem
import ki.product.model.Product
import ki.product.repository.ProductRepository.DbError
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class ProductRepositoryImpl(
    private val databaseFactory: DatabaseFactory,
) : ProductRepository {
    override fun getCheapestItemByCategory(category: Product.Category): Effect<DbError, CategoryItem> = {
        try {
            val result = databaseFactory.dbExec {
                Products.join(Brands, JoinType.INNER, onColumn = Products.brandId, otherColumn = Brands.id)
                    .selectAll()
                    .where(
                        (Products.category eq category) and (Products.deleted eq null),
                    )
                    .orderBy(Products.price, SortOrder.ASC).limit(1)
                    .first()
            }

            CategoryItem(
                brandName = result[Brands.name],
                price = result[Products.price],
            )
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun getMostExpensiveItemByCategory(category: Product.Category): Effect<DbError, CategoryItem> = {
        try {
            val result = databaseFactory.dbExec {
                Products.join(Brands, JoinType.INNER, onColumn = Products.brandId, otherColumn = Brands.id)
                    .selectAll()
                    .where(
                        (Products.category eq category) and (Products.deleted eq null),
                    )
                    .orderBy(Products.price, SortOrder.DESC).limit(1)
                    .first()
            }

            CategoryItem(
                brandName = result[Brands.name],
                price = result[Products.price],
            )
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun getProductsByBrandId(brandId: String): Effect<DbError, List<Product>> = {
        try {
            databaseFactory.dbExec {
                val brand = Brands.selectAll()
                    .where(Brands.id eq brandId and (Brands.deleted eq null))
                    .first()
                    .let { rowResult ->
                        Brand(
                            id = rowResult[Brands.id],
                            name = rowResult[Brands.name],
                            created = Instant.fromEpochMilliseconds(rowResult[Brands.created]),
                            modified = rowResult[Brands.modified]?.let { Instant.fromEpochMilliseconds(it) },
                        )
                    }

                Products.selectAll()
                    .where((Products.brandId eq brandId) and (Products.deleted eq null))
                    .toList()
                    .map {
                        it.toDomainProduct(brand)
                    }
            }
        } catch (e: NoSuchElementException) {
            emptyList()
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun create(product: Product): Effect<DbError, Product> = effect {
        try {
            databaseFactory.dbExec {
                Products.insert { products ->
                    products[id] = product.id
                    products[price] = product.price
                    products[brandId] = product.brand.id
                    products[category] = product.category
                    products[created] = product.created.toEpochMilliseconds()
                }
            }

            product
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun get(id: String): Effect<DbError, Product?> = {
        try {
            databaseFactory.dbExec {
                Products.join(Brands, JoinType.INNER, onColumn = Products.brandId, otherColumn = Brands.id)
                    .selectAll()
                    .where(
                        (Products.id eq id) and (Products.deleted eq null),
                    )
                    .first()
                    .toDomainProduct()
            }
        } catch (e: NoSuchElementException) {
            null
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun update(product: Product): Effect<DbError, Product> = effect {
        try {
            databaseFactory.dbExec {
                Products.update({ (Products.id eq product.id) and (Products.deleted eq null) }) {
                    it[price] = product.price
                    it[brandId] = product.brand.id
                    it[category] = product.category
                    it[modified] = Clock.System.now().toEpochMilliseconds()
                }
            }

            product
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun delete(id: String): Effect<DbError, Unit> = effect {
        try {
            databaseFactory.dbExec {
                Products.update({ (Products.id eq id) and (Products.deleted eq null) }) {
                    it[deleted] = Clock.System.now().toEpochMilliseconds()
                }
            }
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override fun bulkCreate(products: List<Product>): Effect<DbError, Unit> = {
        try {
            databaseFactory.dbExec {
                Products.batchInsert(products) { product ->
                    this[Products.id] = product.id
                    this[Products.brandId] = product.brand.id
                    this[Products.price] = product.price
                    this[Products.category] = product.category
                    this[Products.created] = product.created.toEpochMilliseconds()
                    this[Products.modified] = product.created.toEpochMilliseconds()
                }
            }
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    private fun ResultRow.toDomainProduct(): Product =
        Product(
            id = this[Products.id],
            brand = Brand(
                id = this[Brands.id],
                name = this[Brands.name],
                created = Instant.fromEpochMilliseconds(this[Brands.created]),
                modified = this[Brands.modified]?.let { Instant.fromEpochMilliseconds(it) },
            ),
            price = this[Products.price],
            category = this[Products.category],
            created = Instant.fromEpochMilliseconds(this[Products.created]),
            modified = this[Products.modified]?.let { Instant.fromEpochMilliseconds(it) },
        )

    private fun ResultRow.toDomainProduct(brand: Brand): Product =
        Product(
            id = this[Products.id],
            brand = brand,
            price = this[Products.price],
            category = this[Products.category],
            created = Instant.fromEpochMilliseconds(this[Products.created]),
            modified = this[Products.modified]?.let { Instant.fromEpochMilliseconds(it) },
        )

    private fun Product.Category.toEntityColumn() =
        when (this) {
            Product.Category.TOP -> "top"
            Product.Category.OUTER -> "outer"
            Product.Category.PANTS -> "pants"
            Product.Category.SNEAKERS -> "sneakers"
            Product.Category.BAG -> "bag"
            Product.Category.CAP -> "cap"
            Product.Category.SOCKS -> "socks"
            Product.Category.ACCESSORY -> "accessory"
        }
}

package ki.product.repository

import arrow.core.raise.Effect
import arrow.core.raise.effect
import ki.product.config.DatabaseFactory
import ki.product.entity.Products
import ki.product.model.CategoryItem
import ki.product.model.Product
import ki.product.repository.ProductRepository.Failure
import ki.product.repository.ProductRepository.ReadFailure
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class ProductRepositoryImpl(
    private val databaseFactory: DatabaseFactory,
) : ProductRepository {
    override fun getCheapestItemByCategory(category: Product.Category): Effect<ReadFailure, CategoryItem> = effect {
        try {
            val result = databaseFactory.dbExec {
                Products.selectAll()
                    .orderBy(category.toEntityColumn(), SortOrder.ASC).limit(1)
                    .first()
            }

            CategoryItem(
                brandName = result[Products.brandName],
                price = result[category.toEntityColumn()],
            )
        } catch (e: NoSuchElementException) {
            raise(ReadFailure.NoData())
        } catch (e: Exception) {
            raise(ReadFailure.DbError(e.message, e))
        }
    }

    override fun getMostExpensiveItemByCategory(category: Product.Category): Effect<ReadFailure, CategoryItem> =
        effect {
            try {
                val result = databaseFactory.dbExec {
                    Products.selectAll()
                        .orderBy(category.toEntityColumn(), SortOrder.DESC).limit(1)
                        .first()
                }

                CategoryItem(
                    brandName = result[Products.brandName],
                    price = result[category.toEntityColumn()],
                )
            } catch (e: NoSuchElementException) {
                raise(ReadFailure.NoData())
            } catch (e: Exception) {
                raise(ReadFailure.DbError(e.message, e))
            }
        }

    override fun getCheapestBrand(): Effect<ReadFailure, Product> = effect {
        try {
            val result = databaseFactory.dbExec {
                Products.selectAll()
                    .orderBy(Products.total, SortOrder.ASC).limit(1)
                    .first()
            }

            toDomainProduct(result)
        } catch (e: NoSuchElementException) {
            raise(ReadFailure.NoData())
        } catch (e: Exception) {
            raise(ReadFailure.DbError(e.message, e))
        }
    }

    override fun getProductByBrandName(brandName: String): Effect<Failure, Product?> = effect {
        try {
            val result = databaseFactory.dbExec {
                Products.selectAll()
                    .where(Products.brandName eq brandName)
                    .first()
            }

            toDomainProduct(result)
        } catch (e: NoSuchElementException) {
            null
        } catch (e: Exception) {
            raise(Failure.DbError(e.message, e))
        }
    }

    override fun createProduct(product: Product): Effect<Failure, Product> = effect {
        try {
            databaseFactory.dbExec {
                Products.insert { products ->
                    products[id] = product.id
                    products[brandName] = product.brandName
                    products[top] = product.top
                    products[outer] = product.outer
                    products[pants] = product.pants
                    products[sneakers] = product.sneakers
                    products[bag] = product.bag
                    products[cap] = product.cap
                    products[socks] = product.socks
                    products[accessory] = product.accessory
                    products[total] = product.total
                    products[created] = product.created.toEpochMilliseconds()
                }
            }

            product
        } catch (e: Exception) {
            raise(Failure.DbError(e.message, e))
        }
    }

    override fun getProduct(brandName: String): Effect<ReadFailure, Product> = effect {
        try {
            val result = databaseFactory.dbExec {
                Products.selectAll()
                    .where(Products.brandName eq brandName)
                    .first()
            }

            toDomainProduct(result)
        } catch (e: NoSuchElementException) {
            raise(ReadFailure.NoData())
        } catch (e: Exception) {
            raise(ReadFailure.DbError(e.message, e))
        }
    }

    override fun updateProduct(product: Product): Effect<Failure, Product> = effect {
        try {
            databaseFactory.dbExec {
                Products.update({ Products.id eq product.id }) {
                    it[brandName] = product.brandName
                    it[top] = product.top
                    it[outer] = product.outer
                    it[pants] = product.pants
                    it[sneakers] = product.sneakers
                    it[bag] = product.bag
                    it[cap] = product.cap
                    it[socks] = product.socks
                    it[accessory] = product.accessory
                    it[total] = product.total
                    it[modified] = product.modified?.toEpochMilliseconds()
                }
            }

            product
        } catch (e: Exception) {
            raise(Failure.DbError(e.message, e))
        }
    }

    override fun deleteProduct(brandName: String): Effect<ReadFailure, Unit> = effect {
        try {
            databaseFactory.dbExec {
                Products.deleteWhere {
                    Products.brandName eq brandName
                }
            }
        } catch (e: NoSuchElementException) {
            raise(ReadFailure.NoData())
        } catch (e: Exception) {
            raise(ReadFailure.DbError(e.message, e))
        }
    }

    private fun toDomainProduct(row: ResultRow): Product =
        Product(
            id = row[Products.id],
            brandName = row[Products.brandName],
            top = row[Products.top],
            outer = row[Products.outer],
            pants = row[Products.pants],
            sneakers = row[Products.sneakers],
            bag = row[Products.bag],
            cap = row[Products.cap],
            socks = row[Products.socks],
            accessory = row[Products.accessory],
            total = row[Products.total],
            created = Instant.fromEpochMilliseconds(row[Products.created]),
            modified = row[Products.modified]?.let { Instant.fromEpochMilliseconds(it) },
            deleted = row[Products.deleted]?.let { Instant.fromEpochMilliseconds(it) },
        )

    private fun Product.Category.toEntityColumn() =
        when (this) {
            Product.Category.TOP -> Products.top
            Product.Category.OUTER -> Products.outer
            Product.Category.PANTS -> Products.pants
            Product.Category.SNEAKERS -> Products.sneakers
            Product.Category.BAG -> Products.bag
            Product.Category.CAP -> Products.cap
            Product.Category.SOCKS -> Products.socks
            Product.Category.ACCESSORY -> Products.accessory
        }
}

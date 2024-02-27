package ki.product.repository

import arrow.core.raise.Effect
import arrow.core.raise.effect
import ki.product.config.DatabaseFactory
import ki.product.entity.Products
import ki.product.model.CategoryItem
import ki.product.model.Product
import ki.product.repository.ProductRepository.Failure
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll

class ProductRepositoryImpl(
    private val databaseFactory: DatabaseFactory,
) : ProductRepository {
    override fun getCheapestItemByCategory(category: Product.Category): Effect<Failure, CategoryItem> = effect {
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
            raise(Failure.NoData())
        } catch (e: Exception) {
            raise(Failure.DbError(e.message, e))
        }
    }

    override fun getCheapestBrand(): Effect<Failure, Product> = effect {
        try {
            val result = databaseFactory.dbExec {
                Products.selectAll()
                    .orderBy(Products.total, SortOrder.ASC).limit(1)
                    .first()
            }

            toDomainProduct(result)
        } catch (e: NoSuchElementException) {
            raise(Failure.NoData())
        } catch (e: Exception) {
            raise(Failure.DbError(e.message, e))
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

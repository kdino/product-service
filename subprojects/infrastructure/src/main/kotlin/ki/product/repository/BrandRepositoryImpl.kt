package ki.product.repository

import arrow.core.raise.Effect
import ki.product.config.DatabaseFactory
import ki.product.entity.Brands
import ki.product.entity.Products
import ki.product.model.Brand
import ki.product.repository.BrandRepository.DbError
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class BrandRepositoryImpl(
    private val databaseFactory: DatabaseFactory,
) : BrandRepository {
    override suspend fun getById(id: String): Effect<DbError, Brand?> = {
        try {
            databaseFactory.dbExec {
                Brands.selectAll()
                    .where(Brands.id.eq(id) and (Brands.deleted eq null))
                    .first()
            }
                .let { rowResult ->
                    Brand(
                        id = rowResult[Brands.id],
                        name = rowResult[Brands.name],
                        created = Instant.fromEpochMilliseconds(rowResult[Brands.created]),
                        modified = rowResult[Brands.modified]?.let { Instant.fromEpochMilliseconds(it) },
                    )
                }
        } catch (e: NoSuchElementException) {
            null
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override suspend fun getByName(name: String): Effect<DbError, Brand?> = {
        try {
            databaseFactory.dbExec {
                Brands.selectAll()
                    .where(Brands.name.eq(name) and (Brands.deleted eq null))
                    .first()
            }
                .let { rowResult ->
                    Brand(
                        id = rowResult[Brands.id],
                        name = rowResult[Brands.name],
                        created = Instant.fromEpochMilliseconds(rowResult[Brands.created]),
                        modified = rowResult[Brands.modified]?.let { Instant.fromEpochMilliseconds(it) },
                    )
                }
        } catch (e: NoSuchElementException) {
            null
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override suspend fun getAll(): Effect<DbError, List<Brand>> = {
        try {
            databaseFactory.dbExec {
                Brands.selectAll()
                    .where(Brands.deleted eq null)
                    .orderBy(Brands.name)
                    .toList()
            }
                .map { rowResult ->
                    Brand(
                        id = rowResult[Brands.id],
                        name = rowResult[Brands.name],
                        created = Instant.fromEpochMilliseconds(rowResult[Brands.created]),
                        modified = rowResult[Brands.modified]?.let { Instant.fromEpochMilliseconds(it) },
                    )
                }
        } catch (e: NoSuchElementException) {
            emptyList()
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override suspend fun create(brand: Brand): Effect<DbError, Brand> = {
        try {
            databaseFactory.dbExec {
                Brands.insert { brands ->
                    brands[id] = brand.id
                    brands[name] = brand.name
                    brands[created] = brand.created.toEpochMilliseconds()
                }
            }

            brand
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override suspend fun update(brand: Brand): Effect<DbError, Brand> = {
        try {
            databaseFactory.dbExec {
                Brands.update({ (Brands.id eq brand.id) and (Brands.deleted eq null) }) {
                    it[name] = brand.name
                    it[Products.modified] = Clock.System.now().toEpochMilliseconds()
                }
            }

            brand
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }

    override suspend fun delete(id: String): Effect<DbError, Unit> = {
        try {
            databaseFactory.dbExec {
                Brands.update({ (Brands.id eq id) and (Brands.deleted eq null) }) {
                    it[Products.deleted] = Clock.System.now().toEpochMilliseconds()
                }
            }
        } catch (e: Exception) {
            raise(DbError(e.message))
        }
    }
}

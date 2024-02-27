package ki.product.service

import arrow.core.raise.Effect
import arrow.core.raise.effect
import arrow.core.raise.mapError
import ki.product.model.BrandItem
import ki.product.model.CategoryItem
import ki.product.model.Product
import ki.product.repository.ProductRepository
import ki.product.service.ProductService.CategorySummaryResult
import ki.product.service.ProductService.CheapestBrandResult
import ki.product.service.ProductService.CheapestCombinationResult
import ki.product.service.ProductService.Failure
import ki.product.service.ProductService.GetCategorySummaryFailure

class ProductServiceImpl(
    private val productRepository: ProductRepository,
) : ProductService {

    override fun getCheapestCombination(): Effect<Failure, CheapestCombinationResult> = effect {
        val cheapestPriceMap = Product.Category.values().associateWith { category ->
            productRepository.getCheapestItemByCategory(category)
                .mapError {
                    when (it) {
                        is ProductRepository.Failure.DbError ->
                            raise(Failure.InternalServerError(it.message))

                        is ProductRepository.Failure.NoData ->
                            raise(Failure.DataNotFound(it.message))
                    }
                }.bind()
        }
        val totalPrice = cheapestPriceMap.values.sumOf { it.price }

        CheapestCombinationResult(
            top = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.TOP]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.TOP]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            outer = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.OUTER]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.OUTER]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            pants = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.PANTS]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.PANTS]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            sneakers = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.SNEAKERS]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.SNEAKERS]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            bag = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.BAG]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.BAG]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            cap = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.CAP]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.CAP]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            socks = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.SOCKS]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.SOCKS]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            accessory = CategoryItem(
                brandName = cheapestPriceMap[Product.Category.ACCESSORY]?.brandName
                    ?: raise(Failure.InternalServerError("No data")),
                price = cheapestPriceMap[Product.Category.ACCESSORY]?.price
                    ?: raise(Failure.InternalServerError("No data")),
            ),
            total = totalPrice,
        )
    }

    override fun getCheapestBrand(): Effect<Failure, CheapestBrandResult> = effect {
        val product = productRepository.getCheapestBrand().mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    raise(Failure.InternalServerError(it.message))

                is ProductRepository.Failure.NoData ->
                    raise(Failure.DataNotFound(it.message))
            }
        }.bind()

        val brandItems = listOf(
            BrandItem(
                category = Product.Category.TOP,
                price = product.top,
            ),
            BrandItem(
                category = Product.Category.OUTER,
                price = product.outer,
            ),
            BrandItem(
                category = Product.Category.PANTS,
                price = product.pants,
            ),
            BrandItem(
                category = Product.Category.SNEAKERS,
                price = product.sneakers,
            ),
            BrandItem(
                category = Product.Category.BAG,
                price = product.bag,
            ),
            BrandItem(
                category = Product.Category.CAP,
                price = product.cap,
            ),
            BrandItem(
                category = Product.Category.SOCKS,
                price = product.socks,
            ),
            BrandItem(
                category = Product.Category.ACCESSORY,
                price = product.accessory,
            ),
        )

        CheapestBrandResult(
            brandName = product.brandName,
            brandItems = brandItems,
            total = product.total,
        )
    }

    override fun getCategorySummary(
        category: Product.Category,
    ): Effect<GetCategorySummaryFailure, CategorySummaryResult> = effect {
        val cheapest = productRepository.getCheapestItemByCategory(category).mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    raise(GetCategorySummaryFailure.InternalServerError(it.message))

                is ProductRepository.Failure.NoData ->
                    raise(GetCategorySummaryFailure.DataNotFound(it.message))
            }
        }.bind()

        val mostExpensive = productRepository.getMostExpensiveItemByCategory(category).mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    raise(GetCategorySummaryFailure.InternalServerError(it.message))

                is ProductRepository.Failure.NoData ->
                    raise(GetCategorySummaryFailure.DataNotFound(it.message))
            }
        }.bind()

        CategorySummaryResult(
            category = category,
            cheapest = cheapest,
            mostExpensive = mostExpensive,
        )
    }
}

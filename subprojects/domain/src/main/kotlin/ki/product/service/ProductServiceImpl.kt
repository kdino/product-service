package ki.product.service

import arrow.core.raise.Effect
import arrow.core.raise.effect
import arrow.core.raise.getOrElse
import ki.product.model.Product
import ki.product.model.ProductSummary
import ki.product.repository.ProductRepository
import ki.product.service.ProductService.CheapestCombinationResult
import ki.product.service.ProductService.Failure

class ProductServiceImpl(
    private val productRepository: ProductRepository,
) : ProductService {

    override fun getCheapestCombination(): Effect<Failure, CheapestCombinationResult> =
        effect {
            val cheapestPriceMap = Product.Category.values().associateWith { category ->
                productRepository.getCheapestBrandByCategory(category)
                    .getOrElse {
                        when (it) {
                            is ProductRepository.Failure.DbError ->
                                raise(Failure.InternalServerError(it.message))
                            is ProductRepository.Failure.NoData ->
                                raise(Failure.DataNotFound(it.message))
                        }
                    }
            }
            val totalPrice = cheapestPriceMap.values.sumOf { it.price }

            CheapestCombinationResult(
                top = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.TOP]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.TOP]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                outer = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.OUTER]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.OUTER]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                pants = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.PANTS]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.PANTS]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                sneakers = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.SNEAKERS]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.SNEAKERS]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                bag = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.BAG]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.BAG]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                cap = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.CAP]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.CAP]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                socks = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.SOCKS]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.SOCKS]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                accessory = ProductSummary(
                    brandName = cheapestPriceMap[Product.Category.ACCESSORY]?.brandName
                        ?: raise(Failure.InternalServerError("No data")),
                    price = cheapestPriceMap[Product.Category.ACCESSORY]?.price
                        ?: raise(Failure.InternalServerError("No data")),
                ),
                total = totalPrice,
            )
        }
}

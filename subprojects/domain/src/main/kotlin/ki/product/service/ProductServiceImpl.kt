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
import ki.product.service.ProductService.CreateProductFailure
import ki.product.service.ProductService.Failure
import ki.product.service.ProductService.GetCategorySummaryFailure
import ki.product.service.ProductService.UpdateProductFailure

class ProductServiceImpl(
    private val productRepository: ProductRepository,
) : ProductService {

    override fun getCheapestCombination(): Effect<Failure, CheapestCombinationResult> = effect {
        val cheapestPriceMap = Product.Category.values().associateWith { category ->
            productRepository.getCheapestItemByCategory(category)
                .mapError {
                    when (it) {
                        is ProductRepository.ReadFailure.DbError ->
                            raise(Failure.InternalServerError(it.message))

                        is ProductRepository.ReadFailure.NoData ->
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
                is ProductRepository.ReadFailure.DbError ->
                    raise(Failure.InternalServerError(it.message))

                is ProductRepository.ReadFailure.NoData ->
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
                is ProductRepository.ReadFailure.DbError ->
                    raise(GetCategorySummaryFailure.InternalServerError(it.message))

                is ProductRepository.ReadFailure.NoData ->
                    raise(GetCategorySummaryFailure.DataNotFound(it.message))
            }
        }.bind()

        val mostExpensive = productRepository.getMostExpensiveItemByCategory(category).mapError {
            when (it) {
                is ProductRepository.ReadFailure.DbError ->
                    raise(GetCategorySummaryFailure.InternalServerError(it.message))

                is ProductRepository.ReadFailure.NoData ->
                    raise(GetCategorySummaryFailure.DataNotFound(it.message))
            }
        }.bind()

        CategorySummaryResult(
            category = category,
            cheapest = cheapest,
            mostExpensive = mostExpensive,
        )
    }

    override fun createProduct(product: Product): Effect<CreateProductFailure, Product> = effect {
        productRepository.getProductByBrandName(product.brandName).mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    raise(CreateProductFailure.InternalServerError(it.message))
            }
        }.bind()?.let {
            raise(CreateProductFailure.BrandNameAlreadyExists(it.brandName))
        }

        productRepository.createProduct(product).mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    raise(CreateProductFailure.InternalServerError(it.message))
            }
        }.bind()
    }

    override fun getProduct(brandName: String): Effect<Failure, Product> = effect {
        productRepository.getProduct(brandName).mapError {
            when (it) {
                is ProductRepository.ReadFailure.DbError ->
                    Failure.InternalServerError(it.message)
                is ProductRepository.ReadFailure.NoData ->
                    Failure.DataNotFound(brandName)
            }
        }.bind()
    }

    override fun updateProduct(
        brandName: String,
        updateProductCommand: Product.UpdateCommand,
    ): Effect<UpdateProductFailure, Product> = effect {
        val oldProduct = productRepository.getProductByBrandName(brandName).mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    raise(UpdateProductFailure.InternalServerError(it.message))
            }
        }.bind() ?: raise(UpdateProductFailure.BrandNotFound(brandName))

        // Update를 수행할 브랜드 대상과 바꾸고 싶은 브랜드 이름이 같다면 업데이트 허용
        if (brandName != updateProductCommand.brandName && updateProductCommand.brandName != null) {
            val productWithNewBrandName =
                productRepository.getProductByBrandName(updateProductCommand.brandName).mapError {
                    when (it) {
                        is ProductRepository.Failure.DbError ->
                            raise(UpdateProductFailure.InternalServerError(it.message))
                    }
                }.bind()

            if (productWithNewBrandName != null) {
                raise(UpdateProductFailure.BrandNameAlreadyExists(updateProductCommand.brandName))
            }
        }

        val product = oldProduct.update(updateProductCommand)

        productRepository.updateProduct(product).mapError {
            when (it) {
                is ProductRepository.Failure.DbError ->
                    UpdateProductFailure.InternalServerError(it.message)
            }
        }.bind()
    }

    override fun deleteProduct(brandName: String): Effect<Failure, Unit> = effect {
        productRepository.deleteProduct(brandName).mapError {
            when (it) {
                is ProductRepository.ReadFailure.DbError ->
                    Failure.InternalServerError(it.message)
                is ProductRepository.ReadFailure.NoData ->
                    Failure.DataNotFound(brandName)
            }
        }.bind()
    }
}

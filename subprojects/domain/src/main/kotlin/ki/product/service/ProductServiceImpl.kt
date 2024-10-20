package ki.product.service

import arrow.core.raise.Effect
import arrow.core.raise.ensure
import arrow.core.raise.mapError
import ki.product.model.Product
import ki.product.repository.BrandRepository
import ki.product.repository.ProductRepository
import ki.product.service.ProductService.CategorySummaryResult
import ki.product.service.ProductService.CheapestBrandResult
import ki.product.service.ProductService.CheapestCombinationResult
import ki.product.service.ProductService.CreateProductFailure
import ki.product.service.ProductService.DeleteProductFailure
import ki.product.service.ProductService.GetSummaryFailure
import ki.product.service.ProductService.UpdateProductFailure

class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
) : ProductService {

    override fun getCheapestCombination(): Effect<GetSummaryFailure, CheapestCombinationResult> = {
        val cheapestPriceMap = Product.Category.values().associateWith { category ->
            productRepository.getCheapestItemByCategory(category)
                .mapError { ProductService.InternalError(it.message) }.bind()
        }
        val totalPrice = cheapestPriceMap.values.sumOf { it.price }

        CheapestCombinationResult(
            categories = cheapestPriceMap,
            total = totalPrice,
        )
    }

    override fun getCheapestBrand(): Effect<ProductService.GetCheapestBrandFailure, CheapestBrandResult> = {
        val brands = brandRepository.getAll().mapError {
            ProductService.InternalError(it.message)
        }.bind()
        ensure(brands.isNotEmpty()) { ProductService.BrandNotFound }

        var cheapestBrand = brands.first()
        var cheapestCombination: Map<Product.Category, Product> = emptyMap()
        var cheapestPrice = Int.MAX_VALUE

        brands.map { brand ->
            cheapestCombination = productRepository.getProductsByBrandId(brand.id).mapError {
                ProductService.InternalError(it.message)
            }.bind()
                .groupBy { it.category }
                .mapValues { it.value.minBy { product -> product.price } }

            val totalPrice = cheapestCombination.values.sumOf { it.price }

            if (cheapestPrice > totalPrice) {
                cheapestBrand = brand
                cheapestPrice = totalPrice
            }
        }

        CheapestBrandResult(
            brandName = cheapestBrand.name,
            categories = cheapestCombination,
            total = cheapestPrice,
        )
    }

    override fun getCategorySummary(
        category: Product.Category,
    ): Effect<GetSummaryFailure, CategorySummaryResult> = {
        val cheapest = productRepository.getCheapestItemByCategory(category).mapError {
            ProductService.InternalError(it.message)
        }.bind()

        val mostExpensive = productRepository.getMostExpensiveItemByCategory(category).mapError {
            ProductService.InternalError(it.message)
        }.bind()

        CategorySummaryResult(
            category = category,
            cheapest = cheapest,
            mostExpensive = mostExpensive,
        )
    }

    override fun createProduct(command: Product.CreateCommand): Effect<CreateProductFailure, Product> = {
        val brand = brandRepository.getById(command.brandId).mapError {
            ProductService.InternalError(it.message)
        }.bind()
            ?: raise(ProductService.BrandNotFound) // 브랜드가 없는 경우 에러처리

        val product = Product.create(
            price = command.price,
            brand = brand,
            category = command.category,
        )

        productRepository.create(product).mapError {
            ProductService.InternalError(it.message)
        }.bind()
    }

    override fun updateProduct(
        id: String,
        command: Product.UpdateCommand,
    ): Effect<UpdateProductFailure, Product> = {
        val oldProduct = productRepository.get(id).mapError {
            ProductService.InternalError(it.message)
        }.bind() ?: raise(ProductService.ProductNotFound)

        // brandId가 주어진 경우 Brand 업데이트
        val brand = command.brandId?.let {
            brandRepository.getById(it).mapError { ProductService.InternalError(it.message) }.bind()
                ?: raise(ProductService.BrandNotFound) // 수정하려는 브랜드가 존재하지 않을 경우 에러처리
        }

        val product = oldProduct.update(
            price = command.price,
            category = command.category,
            brand = brand,
        )

        productRepository.update(product).mapError {
            ProductService.InternalError(it.message)
        }.bind()
    }

    override fun deleteProduct(id: String): Effect<DeleteProductFailure, Unit> = {
        productRepository.get(id).mapError {
            ProductService.InternalError(it.message)
        }.bind() ?: raise(ProductService.ProductNotFound)

        productRepository.delete(id).mapError {
            ProductService.InternalError(it.message)
        }.bind()
    }
}

package ki.product.service

import arrow.core.raise.Effect
import arrow.core.raise.ensure
import arrow.core.raise.mapError
import ki.product.model.Brand
import ki.product.model.Product
import ki.product.repository.BrandRepository
import ki.product.repository.ProductRepository
import ki.product.service.BrandService.CreateBrandCommand

class BrandServiceImpl(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
) : BrandService {
    override suspend fun getBrands(): Effect<BrandService.GetBrandsFailure, List<Brand>> = {
        brandRepository.getAll().mapError {
            BrandService.InternalError(it.message)
        }.bind()
    }

    override suspend fun createBrand(command: CreateBrandCommand): Effect<BrandService.CreateBrandFailure, Brand> = {
        val savedBrand = brandRepository.getByName(command.name).mapError {
            BrandService.InternalError(it.message)
        }.bind()
        // 이미 존재하는 브랜드 명인 경우 에러처리
        ensure(savedBrand == null) { BrandService.BrandNameAlreadyExists }

        // 브랜드를 추가하고, 각 브랜드에는 최소 하나의 상품이 존재해야 하므로 상품도 하나씩 추가한다.
        val brand = Brand.create(command.name)
        brandRepository.create(brand).mapError { BrandService.InternalError(it.message) }.bind().apply {
            val products = makeProducts(command, brand)
            productRepository.bulkCreate(products).mapError { BrandService.InternalError(it.message) }.bind()
        }
    }

    override suspend fun updateBrand(id: String, name: String): Effect<BrandService.UpdateBrandFailure, Brand> = {
        val oldBrand = brandRepository.getById(id).mapError {
            BrandService.InternalError(it.message)
        }.bind()
        // 업데이트할 브랜드가 존재하지 않는다면 에러처리
        ensure(oldBrand != null) { BrandService.BrandNotFound }

        val savedBrand = brandRepository.getByName(name).mapError {
            BrandService.InternalError(it.message)
        }.bind()
        // 이미 존재하는 브랜드 명인 경우 에러처리
        ensure(savedBrand == null) { BrandService.BrandNameAlreadyExists }

        brandRepository.update(oldBrand.update(name = name)).mapError {
            BrandService.InternalError(it.message)
        }.bind()
    }

    override suspend fun deleteBrand(id: String): Effect<BrandService.DeleteBrandFailure, Unit> = {
        val oldBrand = brandRepository.getById(id).mapError {
            BrandService.InternalError(it.message)
        }.bind()
        // 업데이트할 브랜드가 존재하지 않는다면 에러처리
        ensure(oldBrand != null) { BrandService.BrandNotFound }

        brandRepository.delete(id).mapError { BrandService.InternalError(it.message) }.bind()
    }

    private fun makeProducts(command: CreateBrandCommand, brand: Brand): List<Product> =
        listOf(
            Product.create(
                price = command.top,
                brand = brand,
                category = Product.Category.TOP,
            ),
            Product.create(
                price = command.bag,
                brand = brand,
                category = Product.Category.BAG,
            ),
            Product.create(
                price = command.cap,
                brand = brand,
                category = Product.Category.CAP,
            ),
            Product.create(
                price = command.outer,
                brand = brand,
                category = Product.Category.OUTER,
            ),
            Product.create(
                price = command.pants,
                brand = brand,
                category = Product.Category.PANTS,
            ),
            Product.create(
                price = command.accessory,
                brand = brand,
                category = Product.Category.ACCESSORY,
            ),
            Product.create(
                price = command.sneakers,
                brand = brand,
                category = Product.Category.SNEAKERS,
            ),
            Product.create(
                price = command.socks,
                brand = brand,
                category = Product.Category.SOCKS,
            ),
        )
}

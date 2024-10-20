package ki.product

import arrow.core.raise.effect
import arrow.core.raise.toEither
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.mockk
import ki.product.model.Brand
import ki.product.model.CategoryItem
import ki.product.model.Product
import ki.product.repository.BrandRepository
import ki.product.repository.ProductRepository
import ki.product.service.ProductService
import ki.product.service.ProductServiceImpl

class ProductServiceTest : FreeSpec({
    "ProductServiceTest" - {
        val productRepository = mockk<ProductRepository>()
        val brandRepository = mockk<BrandRepository>()
        val productService = ProductServiceImpl(
            productRepository,
            brandRepository,
        )

        "createProductTest" - {
            val brand = Brand.create("MyBrand")
            val product = Product.create(
                price = 1000,
                brand = brand,
                category = Product.Category.CAP,
            )

            "success" {
                coEvery {
                    brandRepository.getById(brand.id)
                } returns effect { brand }

                coEvery {
                    productRepository.create(any())
                } returns effect { product }

                productService.createProduct(
                    Product.CreateCommand(
                        price = 1000,
                        brandId = brand.id,
                        category = Product.Category.CAP,
                    ),
                ).toEither().shouldBeRight()
            }

            "fails if brand is not found" {
                coEvery {
                    brandRepository.getById(brand.id)
                } returns effect { null }

                productService.createProduct(
                    Product.CreateCommand(
                        price = 1000,
                        brandId = brand.id,
                        category = Product.Category.CAP,
                    ),
                ).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.BrandNotFound>()
            }
        }

        "updateProductTest" - {
            val brand = Brand.create("MyBrand")
            val newBrand = Brand.create("newBrand")
            val oldProduct = Product.create(
                price = 1000,
                brand = brand,
                category = Product.Category.CAP,
            )

            val newProduct = oldProduct.update(
                price = 2000,
                brand = null,
                category = null,
            )

            "success" {
                coEvery {
                    productRepository.get(oldProduct.id)
                } returns effect { oldProduct }

                coEvery {
                    brandRepository.getById(oldProduct.brand.id)
                } returns effect { brand }

                coEvery {
                    productRepository.update(newProduct)
                } returns effect { newProduct }

                productService.updateProduct(
                    id = oldProduct.id,
                    command = Product.UpdateCommand(
                        price = 2000,
                        brandId = null,
                        category = null,
                    ),
                ).toEither().shouldBeRight()
            }

            "fails if target product is not found" {
                coEvery {
                    productRepository.get(oldProduct.id)
                } returns effect { null }

                productService.updateProduct(
                    id = oldProduct.id,
                    command = Product.UpdateCommand(
                        price = 2000,
                        brandId = null,
                        category = null,
                    ),
                ).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.ProductNotFound>()
            }

            "fails if changing brand does not exist" {
                coEvery {
                    productRepository.get(oldProduct.id)
                } returns effect { oldProduct }

                coEvery {
                    brandRepository.getById(newBrand.id)
                } returns effect { null }

                productService.updateProduct(
                    id = oldProduct.id,
                    command = Product.UpdateCommand(
                        price = 2000,
                        brandId = newBrand.id,
                        category = null,
                    ),
                ).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.BrandNotFound>()
            }
        }

        "deleteProductTest" - {
            val brand = Brand.create("MyBrand")
            val oldProduct = Product.create(
                price = 1000,
                brand = brand,
                category = Product.Category.CAP,
            )

            "success" {
                coEvery {
                    productRepository.get(oldProduct.id)
                } returns effect { oldProduct }

                coEvery {
                    productRepository.delete(oldProduct.id)
                } returns effect {}

                productService.deleteProduct(oldProduct.id).toEither().shouldBeRight()
            }

            "fails if target product does not exist" {
                coEvery {
                    productRepository.get(oldProduct.id)
                } returns effect { null }

                productService.deleteProduct(oldProduct.id).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.ProductNotFound>()
            }
        }

        "getCheapestCombinationTest" - {
            val top = CategoryItem("A", 1000)
            val outer = CategoryItem("B", 2000)
            val pants = CategoryItem("C", 3000)
            val sneakers = CategoryItem("D", 4000)
            val bag = CategoryItem("E", 5000)
            val cap = CategoryItem("F", 6000)
            val socks = CategoryItem("G", 7000)
            val accessory = CategoryItem("H", 8000)
            val total = top.price + outer.price + pants.price + sneakers.price + bag.price + cap.price +
                socks.price + accessory.price

            "success" {
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.TOP)
                } returns effect { top }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.OUTER)
                } returns effect { outer }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.PANTS)
                } returns effect { pants }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.SNEAKERS)
                } returns effect { sneakers }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.BAG)
                } returns effect { bag }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.CAP)
                } returns effect { cap }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.SOCKS)
                } returns effect { socks }
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.ACCESSORY)
                } returns effect { accessory }

                val result = productService.getCheapestCombination().toEither().shouldBeRight()
                result.total shouldBe total
            }
        }

        "getCheapestBrand" - {
            val brand = Brand.create("MyBrand")
            val products = listOf(
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.CAP,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.OUTER,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.BAG,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.SOCKS,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.PANTS,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.SNEAKERS,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.TOP,
                ),
                Product.create(
                    price = 1000,
                    brand = brand,
                    category = Product.Category.ACCESSORY,
                ),
            )

            "success" {
                coEvery {
                    brandRepository.getAll()
                } returns effect { listOf(brand) }

                coEvery {
                    productRepository.getProductsByBrandId(brand.id)
                } returns effect { products }

                val result = productService.getCheapestBrand().toEither().shouldBeRight()
                result.total shouldBe 8000
                result.brandName shouldBe "MyBrand"
            }

            "fails if there is no brand exists" {
                coEvery {
                    brandRepository.getAll()
                } returns effect { listOf() }

                productService.getCheapestBrand().toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.BrandNotFound>()
            }
        }

        "getCategorySummary" - {
            val cheapest = CategoryItem("A", 1000)
            val mostExpensive = CategoryItem("B", 10000)

            "success" {
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.CAP)
                } returns effect { cheapest }

                coEvery {
                    productRepository.getMostExpensiveItemByCategory(Product.Category.CAP)
                } returns effect { mostExpensive }

                val result = productService.getCategorySummary(Product.Category.CAP).toEither().shouldBeRight()
                result.cheapest.price shouldBe cheapest.price
                result.mostExpensive.price shouldBe mostExpensive.price
            }
        }
    }
})

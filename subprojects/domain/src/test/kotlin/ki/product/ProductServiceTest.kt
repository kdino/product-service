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
import ki.product.model.CategoryItem
import ki.product.model.Product
import ki.product.repository.ProductRepository
import ki.product.service.ProductService
import ki.product.service.ProductService.CreateProductFailure
import ki.product.service.ProductServiceImpl

class ProductServiceTest : FreeSpec({
    "ProductServiceTest" - {
        val productRepository = mockk<ProductRepository>()
        val productService = ProductServiceImpl(productRepository)

        "createProductTest" - {
            val product = Product.create(
                Product.CreateCommand(
                    brandName = "ki",
                    top = 1000,
                    outer = 2000,
                    pants = 3000,
                    sneakers = 4000,
                    bag = 5000,
                    cap = 6000,
                    socks = 7000,
                    accessory = 8000,
                ),
            )

            "success" {
                coEvery {
                    productRepository.getProductByBrandName(product.brandName)
                } returns effect { null }

                coEvery {
                    productRepository.createProduct(product)
                } returns effect { product }

                productService.createProduct(product).toEither().shouldBeRight()
            }

            "fails if product name already exists" {
                val alreadySavedProduct = Product.create(
                    Product.CreateCommand(
                        brandName = "ki",
                        top = 1000,
                        outer = 2000,
                        pants = 3000,
                        sneakers = 4000,
                        bag = 5000,
                        cap = 6000,
                        socks = 7000,
                        accessory = 8000,
                    ),
                )

                coEvery {
                    productRepository.getProductByBrandName(product.brandName)
                } returns effect { alreadySavedProduct }

                coEvery {
                    productRepository.createProduct(product)
                } returns effect { product }

                productService.createProduct(product).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<CreateProductFailure.BrandNameAlreadyExists>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getProductByBrandName(product.brandName)
                } returns effect { raise(ProductRepository.Failure.DbError()) }

                coEvery {
                    productRepository.createProduct(product)
                } returns effect { product }

                productService.createProduct(product).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<CreateProductFailure.InternalServerError>()
            }
        }

        "getProductTest" - {
            val product = Product.create(
                Product.CreateCommand(
                    brandName = "ki",
                    top = 1000,
                    outer = 2000,
                    pants = 3000,
                    sneakers = 4000,
                    bag = 5000,
                    cap = 6000,
                    socks = 7000,
                    accessory = 8000,
                ),
            )

            "success" {
                coEvery {
                    productRepository.getProduct(product.brandName)
                } returns effect { product }

                productService.getProduct(product.brandName).toEither().shouldBeRight()
            }

            "fails if target brandName does not exist" {
                coEvery {
                    productRepository.getProduct(product.brandName)
                } returns effect { raise(ProductRepository.ReadFailure.NoData()) }

                productService.getProduct(product.brandName).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.Failure.DataNotFound>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getProduct(product.brandName)
                } returns effect { raise(ProductRepository.ReadFailure.DbError()) }

                productService.getProduct(product.brandName).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.Failure.InternalServerError>()
            }
        }

        "updateProductTest" - {
            val oldProduct = Product.create(
                Product.CreateCommand(
                    brandName = "ki",
                    top = 1000,
                    outer = 2000,
                    pants = 3000,
                    sneakers = 4000,
                    bag = 5000,
                    cap = 6000,
                    socks = 7000,
                    accessory = 8000,
                ),
            )

            val updateProductCommand =
                Product.UpdateCommand(
                    brandName = "new_ki",
                    top = 2000,
                    outer = 3000,
                    pants = 3000,
                    sneakers = 5000,
                    bag = 5000,
                    cap = 6000,
                    socks = 7000,
                    accessory = 4000,
                )

            val newProduct = oldProduct.update(updateProductCommand)

            "success" {
                coEvery {
                    productRepository.getProductByBrandName(oldProduct.brandName)
                } returns effect { oldProduct }

                coEvery {
                    productRepository.getProductByBrandName(newProduct.brandName)
                } returns effect { null }

                coEvery {
                    productRepository.updateProduct(newProduct)
                } returns effect { newProduct }

                productService.updateProduct(oldProduct.brandName, updateProductCommand).toEither().shouldBeRight()
            }

            "fails if changing brandName already exists" {
                coEvery {
                    productRepository.getProductByBrandName(oldProduct.brandName)
                } returns effect { oldProduct }

                coEvery {
                    productRepository.getProductByBrandName(newProduct.brandName)
                } returns effect { newProduct }

                coEvery {
                    productRepository.updateProduct(newProduct)
                } returns effect { newProduct }

                productService.updateProduct(oldProduct.brandName, updateProductCommand).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.UpdateProductFailure.BrandNameAlreadyExists>()
            }

            "fails if target brandName does not exist" {
                coEvery {
                    productRepository.getProductByBrandName(oldProduct.brandName)
                } returns effect { null }

                coEvery {
                    productRepository.getProductByBrandName(newProduct.brandName)
                } returns effect { null }

                coEvery {
                    productRepository.updateProduct(newProduct)
                } returns effect { newProduct }

                productService.updateProduct(oldProduct.brandName, updateProductCommand).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.UpdateProductFailure.BrandNotFound>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getProductByBrandName(oldProduct.brandName)
                } returns effect { raise(ProductRepository.Failure.DbError()) }

                coEvery {
                    productRepository.getProductByBrandName(newProduct.brandName)
                } returns effect { null }

                coEvery {
                    productRepository.updateProduct(newProduct)
                } returns effect { newProduct }

                productService.updateProduct(oldProduct.brandName, updateProductCommand).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.UpdateProductFailure.InternalServerError>()
            }
        }

        "deleteProductTest" - {
            val product = Product.create(
                Product.CreateCommand(
                    brandName = "ki",
                    top = 1000,
                    outer = 2000,
                    pants = 3000,
                    sneakers = 4000,
                    bag = 5000,
                    cap = 6000,
                    socks = 7000,
                    accessory = 8000,
                ),
            )

            "success" {
                coEvery {
                    productRepository.getProductByBrandName(product.brandName)
                } returns effect { product }

                coEvery {
                    productRepository.deleteProduct(product.brandName)
                } returns effect {}

                productService.deleteProduct(product.brandName).toEither().shouldBeRight()
            }

            "fails if target brandName does not exist" {
                coEvery {
                    productRepository.getProductByBrandName(product.brandName)
                } returns effect { null }

                coEvery {
                    productRepository.deleteProduct(product.brandName)
                } returns effect {}

                productService.deleteProduct(product.brandName).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.DeleteProductFailure.BrandNotFound>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getProductByBrandName(product.brandName)
                } returns effect { raise(ProductRepository.Failure.DbError()) }

                coEvery {
                    productRepository.deleteProduct(product.brandName)
                } returns effect {}

                productService.deleteProduct(product.brandName).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.DeleteProductFailure.InternalServerError>()
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

            "fails if there is no data" {
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.TOP)
                } returns effect { raise(ProductRepository.ReadFailure.NoData()) }

                productService.getCheapestCombination().toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.Failure.DataNotFound>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.TOP)
                } returns effect { raise(ProductRepository.ReadFailure.DbError()) }

                productService.getCheapestCombination().toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.Failure.InternalServerError>()
            }
        }

        "getCheapestBrand" - {
            val product = Product.create(
                Product.CreateCommand(
                    brandName = "ki",
                    top = 1000,
                    outer = 2000,
                    pants = 3000,
                    sneakers = 4000,
                    bag = 5000,
                    cap = 6000,
                    socks = 7000,
                    accessory = 8000,
                ),
            )

            "success" {
                coEvery {
                    productRepository.getCheapestBrand()
                } returns effect { product }

                productService.getCheapestBrand().toEither().shouldBeRight()
            }

            "fails if there is no data" {
                coEvery {
                    productRepository.getCheapestBrand()
                } returns effect { raise(ProductRepository.ReadFailure.NoData()) }

                productService.getCheapestBrand().toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.Failure.DataNotFound>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getCheapestBrand()
                } returns effect { raise(ProductRepository.ReadFailure.DbError()) }

                productService.getCheapestBrand().toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.Failure.InternalServerError>()
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

            "fails if there is no data" {
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.CAP)
                } returns effect { raise(ProductRepository.ReadFailure.NoData()) }

                coEvery {
                    productRepository.getMostExpensiveItemByCategory(Product.Category.CAP)
                } returns effect { raise(ProductRepository.ReadFailure.NoData()) }

                productService.getCategorySummary(Product.Category.CAP).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.GetCategorySummaryFailure.DataNotFound>()
            }

            "fails if internal error in repository" {
                coEvery {
                    productRepository.getCheapestItemByCategory(Product.Category.CAP)
                } returns effect { raise(ProductRepository.ReadFailure.DbError()) }

                coEvery {
                    productRepository.getMostExpensiveItemByCategory(Product.Category.CAP)
                } returns effect { raise(ProductRepository.ReadFailure.DbError()) }

                productService.getCategorySummary(Product.Category.CAP).toEither()
                    .shouldBeLeft()
                    .shouldBeTypeOf<ProductService.GetCategorySummaryFailure.InternalServerError>()
            }
        }
    }
})

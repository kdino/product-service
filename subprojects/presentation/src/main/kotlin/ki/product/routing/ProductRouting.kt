package ki.product.routing

import arrow.core.raise.fold
import arrow.core.raise.mapError
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import ki.product.dto.error.BrandNotFoundErrorResponse
import ki.product.dto.error.InternalErrorResponse
import ki.product.dto.error.ProductNotFoundErrorResponse
import ki.product.dto.error.UnknownCategoryErrorResponse
import ki.product.dto.request.CreateProductRequest
import ki.product.dto.request.UpdateProductRequest
import ki.product.dto.request.toCategory
import ki.product.dto.request.toCommand
import ki.product.dto.response.toResponse
import ki.product.respondError
import ki.product.service.ProductService

object ProductRouting {
    fun Route.productRouting(productService: ProductService) {
        get("/products/cheapest/combination") {
            productService.getCheapestCombination().mapError {
                when (it) {
                    is ProductService.InternalError -> InternalErrorResponse(detail = it.message)
                    ProductService.ProductNotFound -> ProductNotFoundErrorResponse()
                }
            }.fold(
                recover = {
                    call.respondError(it)
                },
                transform = {
                    call.respond(it.toResponse())
                },
            )
        }

        get("/products/cheapest/brand") {
            productService.getCheapestBrand().mapError {
                when (it) {
                    is ProductService.InternalError -> InternalErrorResponse(detail = it.message)
                    ProductService.ProductNotFound -> ProductNotFoundErrorResponse()
                    ProductService.BrandNotFound -> BrandNotFoundErrorResponse()
                }
            }.fold(
                recover = {
                    println(it.detail)
                    call.respondError(it)
                },
                transform = {
                    call.respond(it.toResponse())
                },
            )
        }

        get("/products/category/{category}") {
            val category = call.parameters["category"].toCategory()
                ?: return@get call.respondError(UnknownCategoryErrorResponse())

            productService.getCategorySummary(category).mapError {
                when (it) {
                    is ProductService.InternalError -> InternalErrorResponse(detail = it.message)
                    ProductService.ProductNotFound -> ProductNotFoundErrorResponse()
                }
            }.fold(
                recover = {
                    call.respondError(it)
                },
                transform = {
                    call.respond(it.toResponse())
                },
            )
        }

        post("/products") {
            val createProductRequest = call.receive<CreateProductRequest>()

            productService.createProduct(createProductRequest.toCommand()).mapError {
                when (it) {
                    ProductService.BrandNotFound -> BrandNotFoundErrorResponse()
                    is ProductService.InternalError -> InternalErrorResponse(detail = it.message)
                }
            }.fold(
                recover = {
                    call.respondError(it)
                },
                transform = {
                    call.respond(it.toResponse())
                },
            )
        }

        put("/products/{id}") {
            val id = call.parameters["id"]!!
            val updateProductRequest = call.receive<UpdateProductRequest>()

            productService.updateProduct(id, updateProductRequest.toCommand()).mapError {
                when (it) {
                    ProductService.BrandNotFound -> BrandNotFoundErrorResponse()
                    is ProductService.InternalError -> InternalErrorResponse(detail = it.message)
                    ProductService.ProductNotFound -> ProductNotFoundErrorResponse()
                }
            }.fold(
                recover = {
                    call.respondError(it)
                },
                transform = {
                    call.respond(it.toResponse())
                },
            )
        }

        delete("/products/{id}") {
            val id = call.parameters["id"]!!

            productService.deleteProduct(id).mapError {
                when (it) {
                    is ProductService.InternalError -> InternalErrorResponse(detail = it.message)
                    ProductService.ProductNotFound -> ProductNotFoundErrorResponse()
                }
            }.fold(
                recover = {
                    call.respondError(it)
                },
                transform = {
                    call.respond(Unit)
                },
            )
        }
    }
}

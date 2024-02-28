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
import ki.product.dto.error.BadRequestErrorResponse
import ki.product.dto.error.DataAlreadyExistsErrorResponse
import ki.product.dto.error.DataNotFoundResponse
import ki.product.dto.error.InternalErrorResponse
import ki.product.dto.request.CreateProductRequest
import ki.product.dto.request.UpdateProductRequest
import ki.product.dto.request.toCategory
import ki.product.dto.request.toDomain
import ki.product.dto.request.toUpdateCommand
import ki.product.dto.response.toResponse
import ki.product.respondError
import ki.product.service.ProductService

object ProductRouting {
    fun Route.productRouting(productService: ProductService) {
        get("/products/cheapest/combination") {
            productService.getCheapestCombination().mapError {
                when (it) {
                    is ProductService.Failure.DataNotFound ->
                        DataNotFoundResponse()

                    is ProductService.Failure.InternalServerError ->
                        InternalErrorResponse(it.message)
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
                    is ProductService.Failure.DataNotFound ->
                        DataNotFoundResponse()

                    is ProductService.Failure.InternalServerError ->
                        InternalErrorResponse(it.message)
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
                ?: return@get call.respondError(BadRequestErrorResponse("Not appropriate category"))

            productService.getCategorySummary(category).mapError {
                when (it) {
                    is ProductService.GetCategorySummaryFailure.DataNotFound ->
                        DataNotFoundResponse()

                    is ProductService.GetCategorySummaryFailure.InternalServerError ->
                        InternalErrorResponse(it.message)
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

            productService.createProduct(createProductRequest.toDomain()).mapError {
                when (it) {
                    is ProductService.CreateProductFailure.BrandNameAlreadyExists ->
                        DataAlreadyExistsErrorResponse("Requested brand name already exists - ${it.message}")

                    is ProductService.CreateProductFailure.InternalServerError ->
                        InternalErrorResponse(it.message)
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

        get("/products/brand/{brand}") {
            val brandName = call.parameters["brand"]!!

            productService.getProduct(brandName).mapError {
                when (it) {
                    is ProductService.Failure.DataNotFound ->
                        DataNotFoundResponse("Requested brand name not found - ${it.message}")
                    is ProductService.Failure.InternalServerError ->
                        InternalErrorResponse(it.message)
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

        put("/products/brand/{brand}") {
            val brandName = call.parameters["brand"]!!
            val updateProductRequest = call.receive<UpdateProductRequest>()

            productService.updateProduct(brandName, updateProductRequest.toUpdateCommand()).mapError {
                when (it) {
                    is ProductService.UpdateProductFailure.BrandNameAlreadyExists ->
                        DataAlreadyExistsErrorResponse("Requested brand name already exists - ${it.message}")
                    is ProductService.UpdateProductFailure.BrandNotFound ->
                        DataNotFoundResponse("Requested brand name not found - ${it.message}")
                    is ProductService.UpdateProductFailure.InternalServerError ->
                        InternalErrorResponse(it.message)
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

        delete("/products/brand/{brand}") {
            val brandName = call.parameters["brand"]!!

            productService.deleteProduct(brandName).mapError {
                when (it) {
                    is ProductService.Failure.DataNotFound ->
                        DataNotFoundResponse("Requested brand name not found - ${it.message}")
                    is ProductService.Failure.InternalServerError ->
                        InternalErrorResponse(it.message)
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

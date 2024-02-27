package ki.product.routing

import arrow.core.raise.fold
import arrow.core.raise.mapError
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import ki.product.dto.error.BadRequestErrorResponse
import ki.product.dto.error.DataNotFoundResponse
import ki.product.dto.error.InternalErrorResponse
import ki.product.dto.request.toCategory
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
                ?: return@get call.respondError(BadRequestErrorResponse("올바른 카테고리가 아닙니다."))

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
    }
}

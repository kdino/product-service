package ki.product.routing

import arrow.core.raise.fold
import arrow.core.raise.mapError
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import ki.product.dto.error.InternalErrorResponse
import ki.product.dto.response.toResponse
import ki.product.respondError
import ki.product.service.ProductService

object ProductRouting {
    fun Route.productRouting(productService: ProductService) {
        get("/product/cheapest-combination") {
            productService.getCheapestCombination().mapError {
                ProductService.Failure.InternalServerError(it.message)
            }.fold(
                recover = {
                    call.respondError(InternalErrorResponse(cause = it))
                },
                transform = {
                    call.respond(it.toResponse())
                },
            )
        }
    }
}

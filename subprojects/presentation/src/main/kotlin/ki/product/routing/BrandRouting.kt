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
import ki.product.dto.error.BrandNameAlreadyExists
import ki.product.dto.error.BrandNotFoundErrorResponse
import ki.product.dto.error.InternalErrorResponse
import ki.product.dto.request.CreateBrandRequest
import ki.product.dto.request.UpdateBrandRequest
import ki.product.dto.response.toResponse
import ki.product.respondError
import ki.product.service.BrandService

object BrandRouting {
    fun Route.brandRouting(brandService: BrandService) {
        get("/brands") {
            brandService.getBrands().mapError {
                when (it) {
                    BrandService.BrandNotFound -> BrandNotFoundErrorResponse()
                    is BrandService.InternalError -> InternalErrorResponse(detail = it.message)
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

        post("/brands") {
            val request = call.receive<CreateBrandRequest>()
            val command = BrandService.CreateBrandCommand(
                name = request.name,
                top = request.top,
                outer = request.outer,
                pants = request.pants,
                sneakers = request.sneakers,
                bag = request.bag,
                cap = request.hat,
                socks = request.socks,
                accessory = request.accessory,
            )

            brandService.createBrand(command).mapError {
                when (it) {
                    BrandService.BrandNameAlreadyExists -> BrandNameAlreadyExists()
                    is BrandService.InternalError -> InternalErrorResponse(detail = it.message)
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

        put("/brands/{id}") {
            val id = call.parameters["id"]!!
            val updateBrandRequest = call.receive<UpdateBrandRequest>()

            brandService.updateBrand(id, updateBrandRequest.name).mapError {
                when (it) {
                    BrandService.BrandNotFound -> BrandNotFoundErrorResponse()
                    BrandService.BrandNameAlreadyExists -> BrandNameAlreadyExists()
                    is BrandService.InternalError -> InternalErrorResponse(detail = it.message)
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

        delete("/brands/{id}") {
            val id = call.parameters["id"]!!
            brandService.deleteBrand(id).mapError {
                when (it) {
                    BrandService.BrandNotFound -> BrandNotFoundErrorResponse()
                    is BrandService.InternalError -> InternalErrorResponse(detail = it.message)
                }
            }.fold(
                recover = {
                    call.respondError(it)
                },
                transform = {
                    call.respond("succeeded")
                },
            )
        }
    }
}

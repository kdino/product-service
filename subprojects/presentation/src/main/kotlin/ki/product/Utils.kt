package ki.product

import io.ktor.application.ApplicationCall
import io.ktor.response.respond
import ki.product.dto.error.BaseErrorResponse

suspend inline fun <reified T : BaseErrorResponse> ApplicationCall.respondError(errorResponse: T) {
    respond(errorResponse.statusCode, errorResponse)
}

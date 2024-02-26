package ki.product

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import ki.product.dto.error.BaseErrorResponse

suspend inline fun <reified T : BaseErrorResponse> ApplicationCall.respondError(errorResponse: T) {
    if (errorResponse.statusCode.value < HttpStatusCode.InternalServerError.value) {
        println("Request Error 발생 [${errorResponse.cause?.message}]")
    } else {
        println("Internal Server Error - $errorResponse.cause")
    }

    response.status(errorResponse.statusCode)
    respond(errorResponse)
}

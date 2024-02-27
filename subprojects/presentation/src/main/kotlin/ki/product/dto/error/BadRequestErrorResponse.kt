package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class BadRequestErrorResponse(
    override val detail: String? = "Bad request",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.BadRequest,
) : BaseErrorResponse()

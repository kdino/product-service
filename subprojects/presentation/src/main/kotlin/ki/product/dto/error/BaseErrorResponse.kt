package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class BaseErrorResponse {
    abstract val detail: String?

    @Transient open val statusCode: HttpStatusCode = HttpStatusCode.InternalServerError
}

package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class BaseErrorResponse {
    abstract val detail: String?
    abstract val cause: Throwable?

    @Transient open val statusCode: HttpStatusCode = HttpStatusCode.OK
}

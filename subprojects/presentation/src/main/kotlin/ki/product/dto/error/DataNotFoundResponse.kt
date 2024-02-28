package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class DataNotFoundResponse(
    override val detail: String? = "Data not found error",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.NotFound,
) : BaseErrorResponse()

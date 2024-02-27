package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class DataAlreadyExistsErrorResponse(
    override val detail: String? = "Data already exists error",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.Conflict,
) : BaseErrorResponse()

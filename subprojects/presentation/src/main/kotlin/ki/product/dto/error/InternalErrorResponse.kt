package ki.product.dto.error

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class InternalErrorResponse(
    override val detail: String = "Internal Server Error",
    @Transient override val cause: Throwable? = null,
) : BaseErrorResponse()

package ki.product.dto.error

import kotlinx.serialization.Serializable

@Serializable
class InternalErrorResponse(
    override val detail: String? = "Internal Server Error",
) : BaseErrorResponse()

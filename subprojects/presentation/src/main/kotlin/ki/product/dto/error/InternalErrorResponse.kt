package ki.product.dto.error

import kotlinx.serialization.Serializable

@Serializable
class InternalErrorResponse(
    override val errorCode: Int = 5000,
    override val detail: String? = "서버 에러",
) : BaseErrorResponse()

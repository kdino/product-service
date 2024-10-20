package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class ProductNotFoundErrorResponse(
    override val errorCode: Int = 4002,
    override val detail: String? = "상품을 찾을 수 없습니다.",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.NotFound,
) : BaseErrorResponse()

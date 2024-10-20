package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class BrandNotFoundErrorResponse(
    override val errorCode: Int = 4001,
    override val detail: String? = "브랜드를 찾을 수 없습니다.",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.NotFound,
) : BaseErrorResponse()

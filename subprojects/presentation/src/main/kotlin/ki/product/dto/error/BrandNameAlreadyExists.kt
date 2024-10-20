package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class BrandNameAlreadyExists(
    override val errorCode: Int = 3001,
    override val detail: String? = "브랜드 이름이 이미 존재합니다.",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.BadRequest,
) : BaseErrorResponse()

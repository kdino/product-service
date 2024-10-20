package ki.product.dto.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class UnknownCategoryErrorResponse(
    override val errorCode: Int = 2002,
    override val detail: String? = "매칭되는 카테고리가 없습니다.",
    @Transient
    override val statusCode: HttpStatusCode = HttpStatusCode.BadRequest,
) : BaseErrorResponse()

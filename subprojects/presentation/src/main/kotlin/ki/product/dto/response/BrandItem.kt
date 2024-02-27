package ki.product.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrandItem(
    @SerialName("카테고리")
    val category: String,
    @SerialName("가격")
    val price: Int,
)

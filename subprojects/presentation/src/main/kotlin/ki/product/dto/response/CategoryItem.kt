package ki.product.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItem(
    @SerialName("브랜드")
    val brandName: String,
    @SerialName("가격")
    val price: Int,
)

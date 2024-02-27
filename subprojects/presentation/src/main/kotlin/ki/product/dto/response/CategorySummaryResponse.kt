package ki.product.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategorySummaryResponse(
    @SerialName("카테고리")
    val category: String,
    @SerialName("최저가")
    val cheapest: CategoryItem,
    @SerialName("최고가")
    val mostExpensive: CategoryItem,
)

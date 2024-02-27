package ki.product.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheapestBrandResponse(
    @SerialName("최저가")
    val cheapestPrice: CheapestPrice,
) {
    @Serializable
    data class CheapestPrice(
        @SerialName("브랜드")
        val brandName: String,
        @SerialName("카테고리")
        val brandItems: List<BrandItem>,
        @SerialName("총액")
        val total: Int,
    )
}

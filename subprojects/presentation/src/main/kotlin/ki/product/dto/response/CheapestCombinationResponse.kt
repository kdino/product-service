package ki.product.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheapestCombinationResponse(
    @SerialName("상의")
    val top: ProductSummary,
    @SerialName("아우터")
    val outer: ProductSummary,
    @SerialName("바지")
    val pants: ProductSummary,
    @SerialName("스니커즈")
    val sneakers: ProductSummary,
    @SerialName("가방")
    val bag: ProductSummary,
    @SerialName("모자")
    val cap: ProductSummary,
    @SerialName("양말")
    val socks: ProductSummary,
    @SerialName("액세서리")
    val accessory: ProductSummary,
    @SerialName("총액")
    val total: Int,
) {
    @Serializable
    data class ProductSummary(
        val brandName: String,
        val price: Int,
    )
}

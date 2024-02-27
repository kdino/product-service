package ki.product.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheapestCombinationResponse(
    @SerialName("상의")
    val top: CategoryItem,
    @SerialName("아우터")
    val outer: CategoryItem,
    @SerialName("바지")
    val pants: CategoryItem,
    @SerialName("스니커즈")
    val sneakers: CategoryItem,
    @SerialName("가방")
    val bag: CategoryItem,
    @SerialName("모자")
    val cap: CategoryItem,
    @SerialName("양말")
    val socks: CategoryItem,
    @SerialName("액세서리")
    val accessory: CategoryItem,
    @SerialName("총액")
    val total: Int,
) {
    @Serializable
    data class CategoryItem(
        val brandName: String,
        val price: Int,
    )
}

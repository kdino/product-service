package ki.product.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProductRequest(
    @SerialName("brand_name")
    val brandName: String?,
    val top: Int?,
    val outer: Int?,
    val pants: Int?,
    val sneakers: Int?,
    val bag: Int?,
    val cap: Int?,
    val socks: Int?,
    val accessory: Int?,
)

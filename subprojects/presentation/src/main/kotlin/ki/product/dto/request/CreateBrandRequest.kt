package ki.product.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateBrandRequest(
    val name: String,
    val top: Int,
    val outer: Int,
    val pants: Int,
    val sneakers: Int,
    val bag: Int,
    val hat: Int,
    val socks: Int,
    val accessory: Int,
)

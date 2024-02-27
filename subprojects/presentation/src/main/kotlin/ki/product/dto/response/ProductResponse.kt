package ki.product.dto.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val result: String,
    val product: Product,
) {
    @Serializable
    data class Product(
        val id: String,
        @SerialName("brand_name")
        val brandName: String,
        val top: Int,
        val outer: Int,
        val pants: Int,
        val sneakers: Int,
        val bag: Int,
        val cap: Int,
        val socks: Int,
        val accessory: Int,
        val total: Int,
        val created: Instant,
        val modified: Instant? = null,
        val deleted: Instant? = null,
    )
}

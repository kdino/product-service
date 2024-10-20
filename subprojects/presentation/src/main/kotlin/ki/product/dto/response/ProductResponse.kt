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
        val price: Int,
        @SerialName("brand_name")
        val brandName: String,
        val category: String,
        val created: Instant,
        val modified: Instant? = null,
        val deleted: Instant? = null,
    )
}

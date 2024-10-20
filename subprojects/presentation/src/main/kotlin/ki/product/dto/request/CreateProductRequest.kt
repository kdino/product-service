package ki.product.dto.request

import ki.product.dto.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProductRequest(
    val price: Int,
    @SerialName("brand_id")
    val brandId: String,
    val category: Category,
)

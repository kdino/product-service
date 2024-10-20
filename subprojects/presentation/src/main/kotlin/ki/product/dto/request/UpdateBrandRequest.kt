package ki.product.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBrandRequest(
    val name: String,
)

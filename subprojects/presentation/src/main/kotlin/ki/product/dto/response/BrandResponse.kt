package ki.product.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BrandResponse(
    val id: String,
    val name: String,
)

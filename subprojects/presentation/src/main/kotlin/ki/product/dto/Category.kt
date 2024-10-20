package ki.product.dto

import kotlinx.serialization.Serializable

@Serializable
enum class Category {
    TOP,
    OUTER,
    PANTS,
    SNEAKERS,
    BAG,
    CAP,
    SOCKS,
    ACCESSORY,
}

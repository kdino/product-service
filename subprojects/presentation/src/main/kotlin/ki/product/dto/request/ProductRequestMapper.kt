package ki.product.dto.request

import ki.product.model.Product

fun String?.toCategory() =
    when (this) {
        "top" -> Product.Category.TOP
        "outer" -> Product.Category.OUTER
        "pants" -> Product.Category.PANTS
        "sneakers" -> Product.Category.SNEAKERS
        "bag" -> Product.Category.BAG
        "cap" -> Product.Category.CAP
        "socks" -> Product.Category.SOCKS
        "accessory" -> Product.Category.ACCESSORY
        else -> null
    }

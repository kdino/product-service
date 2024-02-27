package ki.product.dto.request

import ki.product.model.Product

fun CreateProductRequest.toDomain() =
    Product.create(
        Product.CreateCommand(
            brandName = brandName,
            top = top,
            outer = outer,
            pants = pants,
            sneakers = sneakers,
            bag = bag,
            cap = cap,
            socks = socks,
            accessory = accessory,
        ),
    )

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

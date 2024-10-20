package ki.product.dto.request

import ki.product.model.Product
import ki.product.model.Product.Category
import ki.product.dto.Category as CategoryDto

fun CreateProductRequest.toCommand() =
    Product.CreateCommand(
        price = price,
        brandId = brandId,
        category = category.toDomain(),
    )

fun UpdateProductRequest.toCommand() =
    Product.UpdateCommand(
        price = price,
        brandId = brandId,
        category = category?.toDomain(),
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

fun CategoryDto.toDomain() =
    when (this) {
        CategoryDto.TOP -> Category.TOP
        CategoryDto.OUTER -> Category.OUTER
        CategoryDto.PANTS -> Category.PANTS
        CategoryDto.SNEAKERS -> Category.SNEAKERS
        CategoryDto.BAG -> Category.BAG
        CategoryDto.CAP -> Category.CAP
        CategoryDto.SOCKS -> Category.SOCKS
        CategoryDto.ACCESSORY -> Category.ACCESSORY
    }

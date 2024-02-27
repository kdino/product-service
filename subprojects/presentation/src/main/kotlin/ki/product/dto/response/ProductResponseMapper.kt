package ki.product.dto.response

import ki.product.model.Product
import ki.product.service.ProductService.CheapestBrandResult
import ki.product.service.ProductService.CheapestCombinationResult
import ki.product.dto.response.CheapestCombinationResponse.CategoryItem as CategoryItemDto
import ki.product.dto.response.CheapestBrandResponse.BrandItem as BrandItemDto

fun CheapestCombinationResult.toResponse() =
    CheapestCombinationResponse(
        top = CategoryItemDto(
            brandName = top.brandName,
            price = top.price,
        ),
        outer = CategoryItemDto(
            brandName = outer.brandName,
            price = outer.price,
        ),
        pants = CategoryItemDto(
            brandName = pants.brandName,
            price = pants.price,
        ),
        sneakers = CategoryItemDto(
            brandName = sneakers.brandName,
            price = sneakers.price,
        ),
        bag = CategoryItemDto(
            brandName = bag.brandName,
            price = bag.price,
        ),
        cap = CategoryItemDto(
            brandName = cap.brandName,
            price = cap.price,
        ),
        socks = CategoryItemDto(
            brandName = socks.brandName,
            price = socks.price,
        ),
        accessory = CategoryItemDto(
            brandName = accessory.brandName,
            price = accessory.price,
        ),
        total = total,
    )

fun CheapestBrandResult.toResponse() =
    CheapestBrandResponse(
        CheapestBrandResponse.CheapestPrice(
            brandName = brandName,
            brandItems = brandItems.map {
                BrandItemDto(
                    category = it.category.toResponse(),
                    price = it.price
                )
            },
            total = total
        )
    )

fun Product.Category.toResponse() =
    when (this) {
        Product.Category.TOP -> "상의"
        Product.Category.OUTER -> "아우터"
        Product.Category.PANTS -> "바지"
        Product.Category.SNEAKERS -> "스니커즈"
        Product.Category.BAG -> "가방"
        Product.Category.CAP -> "모자"
        Product.Category.SOCKS -> "양말"
        Product.Category.ACCESSORY -> "액세서리"
    }

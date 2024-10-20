package ki.product.dto.response

import ki.product.model.Product
import ki.product.service.ProductService.CategorySummaryResult
import ki.product.service.ProductService.CheapestBrandResult
import ki.product.service.ProductService.CheapestCombinationResult
import ki.product.dto.response.BrandItem as BrandItemDto
import ki.product.dto.response.CategoryItem as CategoryItemDto
import ki.product.dto.response.ProductResponse.Product as ProductDto

fun CheapestCombinationResult.toResponse() =
    CheapestCombinationResponse(
        top = CategoryItemDto(
            brandName = this.categories[Product.Category.TOP]!!.brandName,
            price = this.categories[Product.Category.TOP]!!.price,
        ),
        outer = CategoryItemDto(
            brandName = this.categories[Product.Category.OUTER]!!.brandName,
            price = this.categories[Product.Category.OUTER]!!.price,
        ),
        pants = CategoryItemDto(
            brandName = this.categories[Product.Category.PANTS]!!.brandName,
            price = this.categories[Product.Category.PANTS]!!.price,
        ),
        sneakers = CategoryItemDto(
            brandName = this.categories[Product.Category.SNEAKERS]!!.brandName,
            price = this.categories[Product.Category.SNEAKERS]!!.price,
        ),
        bag = CategoryItemDto(
            brandName = this.categories[Product.Category.BAG]!!.brandName,
            price = this.categories[Product.Category.BAG]!!.price,
        ),
        cap = CategoryItemDto(
            brandName = this.categories[Product.Category.CAP]!!.brandName,
            price = this.categories[Product.Category.CAP]!!.price,
        ),
        socks = CategoryItemDto(
            brandName = this.categories[Product.Category.SOCKS]!!.brandName,
            price = this.categories[Product.Category.SOCKS]!!.price,
        ),
        accessory = CategoryItemDto(
            brandName = this.categories[Product.Category.ACCESSORY]!!.brandName,
            price = this.categories[Product.Category.ACCESSORY]!!.price,
        ),
        total = total,
    )

fun CheapestBrandResult.toResponse() =
    CheapestBrandResponse(
        CheapestBrandResponse.CheapestPrice(
            brandName = brandName,
            brandItems = categories.map {
                BrandItemDto(
                    category = it.key.toResponse(),
                    price = it.value.price,
                )
            },
            total = total,
        ),
    )

fun CategorySummaryResult.toResponse() =
    CategorySummaryResponse(
        category = category.toResponse(),
        cheapest = CategoryItemDto(
            brandName = cheapest.brandName,
            price = cheapest.price,
        ),
        mostExpensive = CategoryItemDto(
            brandName = mostExpensive.brandName,
            price = mostExpensive.price,
        ),
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

fun Product.toResponse() =
    ProductResponse(
        result = "성공",
        product = ProductDto(
            id = id,
            price = price,
            brandName = brand.name,
            category = category.toResponse(),
            created = created,
            modified = modified,
        ),
    )

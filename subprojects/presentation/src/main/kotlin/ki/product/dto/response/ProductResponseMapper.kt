package ki.product.dto.response

import ki.product.service.ProductService.CheapestCombinationResult
import ki.product.dto.response.CheapestCombinationResponse.ProductSummary as ProductSummaryDto

fun CheapestCombinationResult.toResponse() =
    CheapestCombinationResponse(
        top = ProductSummaryDto(
            brandName = top.brandName,
            price = top.price,
        ),
        outer = ProductSummaryDto(
            brandName = outer.brandName,
            price = outer.price,
        ),
        pants = ProductSummaryDto(
            brandName = pants.brandName,
            price = pants.price,
        ),
        sneakers = ProductSummaryDto(
            brandName = sneakers.brandName,
            price = sneakers.price,
        ),
        bag = ProductSummaryDto(
            brandName = bag.brandName,
            price = bag.price,
        ),
        cap = ProductSummaryDto(
            brandName = cap.brandName,
            price = cap.price,
        ),
        socks = ProductSummaryDto(
            brandName = socks.brandName,
            price = socks.price,
        ),
        accessory = ProductSummaryDto(
            brandName = accessory.brandName,
            price = accessory.price,
        ),
    )

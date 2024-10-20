package ki.product.dto.response

import ki.product.model.Brand

fun Brand.toResponse() =
    BrandResponse(
        id = this.id,
        name = this.name,
    )

fun List<Brand>.toResponse() = this.map { it.toResponse() }

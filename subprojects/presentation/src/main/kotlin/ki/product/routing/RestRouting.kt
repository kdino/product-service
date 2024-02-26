package ki.product.routing

import io.ktor.application.Application
import io.ktor.routing.routing
import ki.product.routing.ProductRouting.productRouting
import ki.product.service.ProductService

fun Application.installRestRouting(
    productService: ProductService,
) {
    routing {
        productRouting(productService)
    }
}

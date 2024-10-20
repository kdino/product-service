package ki.product

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ki.product.routing.installRestRouting
import ki.product.service.BrandService
import ki.product.service.ProductService
import kotlinx.serialization.json.Json

class HttpServer(
    private val httpConfiguration: HttpConfiguration,
    private val productService: ProductService,
    private val brandService: BrandService,
) {
    private val embeddedServer by lazy {
        embeddedServer(Netty, port = httpConfiguration.port) {
            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = true
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                        explicitNulls = false
                    },
                )
            }
            installRestRouting(
                productService,
                brandService,
            )
        }
    }

    fun start() {
        embeddedServer.start(wait = false)
    }
}

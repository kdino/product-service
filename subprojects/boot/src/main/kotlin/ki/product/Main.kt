package ki.product

import com.typesafe.config.ConfigFactory
import ki.product.config.DatabaseConfiguration
import ki.product.config.DatabaseFactory
import ki.product.repository.ProductRepository
import ki.product.repository.ProductRepositoryImpl
import ki.product.service.ProductService
import ki.product.service.ProductServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

suspend fun main() {
    val application = Application.load()

    val scope = CoroutineScope(Dispatchers.Default)

    val http = scope.async {
        application.presentation.httpServer.start()
    }

    http.await()
}

data class Application(
    val configuration: ProductServerConfiguration,
    val infrastructure: Infrastructure,
    val domain: Domain,
    val presentation: Presentation,
) {
    companion object {
        fun load(): Application {
            val configuration = ProductServerConfiguration.load()
            val infrastructure = Infrastructure.load(configuration.databaseConfiguration)
            val domain = Domain.load(infrastructure)
            val presentation = Presentation.load(configuration.httpConfiguration, domain)

            return Application(configuration, infrastructure, domain, presentation)
        }
    }
}

data class Presentation(
    val httpServer: HttpServer,
) {
    companion object {
        fun load(httpConfiguration: HttpConfiguration, domain: Domain): Presentation {
            val httpServer = HttpServer(httpConfiguration, domain.productService)

            return Presentation(httpServer)
        }
    }
}

data class Domain(
    val productService: ProductService,
) {
    companion object {
        fun load(
            infrastructure: Infrastructure,
        ): Domain {
            val productService = ProductServiceImpl(infrastructure.productRepository)

            return Domain(
                productService,
            )
        }
    }
}

data class Infrastructure(
    val productRepository: ProductRepository,
) {
    companion object {
        fun load(
            databaseConfiguration: DatabaseConfiguration,
        ): Infrastructure {
            val databaseFactory = DatabaseFactory(databaseConfiguration)
            val productRepository = ProductRepositoryImpl(databaseFactory)

            databaseFactory.connectAndMigrate()

            return Infrastructure(
                productRepository,
            )
        }
    }
}

data class ProductServerConfiguration(
    val httpConfiguration: HttpConfiguration,
    val databaseConfiguration: DatabaseConfiguration,
) {
    companion object {
        fun load(): ProductServerConfiguration {
            val rootConfiguration = ConfigFactory.load()

            val httpConfiguration = HttpConfiguration.load(rootConfiguration)
            val databaseConfiguration = DatabaseConfiguration.load(rootConfiguration)

            return ProductServerConfiguration(
                httpConfiguration,
                databaseConfiguration,
            )
        }
    }
}

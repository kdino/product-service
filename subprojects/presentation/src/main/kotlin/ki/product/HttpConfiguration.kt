package ki.product

import com.typesafe.config.Config

data class HttpConfiguration(
    val port: Int,
) {
    companion object {
        fun load(config: Config): HttpConfiguration =
            HttpConfiguration(
                port = config.getInt("http.port"),
            )
    }
}

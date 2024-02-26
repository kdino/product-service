package ki.product.config

import com.typesafe.config.Config

data class DatabaseConfiguration(
    val host: String,
    val port: Int,
) {
    companion object {
        fun load(config: Config): DatabaseConfiguration =
            DatabaseConfiguration(
                host = config.getString("database.host"),
                port = config.getInt("database.port"),
            )
    }
}

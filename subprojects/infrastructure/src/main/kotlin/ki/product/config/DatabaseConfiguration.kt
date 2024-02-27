package ki.product.config

import com.typesafe.config.Config

data class DatabaseConfiguration(
    val poolSize: Int,
) {
    companion object {
        fun load(config: Config): DatabaseConfiguration =
            DatabaseConfiguration(
                poolSize = config.getInt("database.poolSize"),
            )
    }
}

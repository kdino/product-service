package ki.product.model

import ki.product.common.Utils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Brand(
    val id: String,
    val name: String,
    val created: Instant,
    val modified: Instant? = null,
) {
    companion object {
        fun create(name: String) =
            Brand(
                id = Utils.generateId("brand"),
                name = name,
                created = Clock.System.now(),
            )
    }

    fun update(name: String) =
        copy(
            name = name,
            modified = Clock.System.now(),
        )
}

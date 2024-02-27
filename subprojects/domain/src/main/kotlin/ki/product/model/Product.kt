package ki.product.model

import ki.product.common.Utils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Product(
    val id: String,
    val brandName: String,
    val top: Int,
    val outer: Int,
    val pants: Int,
    val sneakers: Int,
    val bag: Int,
    val cap: Int,
    val socks: Int,
    val accessory: Int,
    val created: Instant,
    val modified: Instant? = null,
    val deleted: Instant? = null,
) {
    fun create(command: CreateCommand) =
        Product(
            id = Utils.generateId(this.javaClass.name),
            brandName = command.brandName,
            top = command.top,
            outer = command.outer,
            pants = command.pants,
            sneakers = command.sneakers,
            bag = command.bag,
            cap = command.cap,
            socks = command.socks,
            accessory = command.accessory,
            created = Clock.System.now(),
        )

    fun update(command: UpdateCommand) =
        copy(
            top = command.top ?: top,
            outer = command.outer ?: outer,
            pants = command.pants ?: pants,
            sneakers = command.sneakers ?: sneakers,
            bag = command.bag ?: bag,
            cap = command.cap ?: cap,
            socks = command.socks ?: socks,
            accessory = command.accessory ?: accessory,
        )

    data class CreateCommand(
        val brandName: String,
        val top: Int,
        val outer: Int,
        val pants: Int,
        val sneakers: Int,
        val bag: Int,
        val cap: Int,
        val socks: Int,
        val accessory: Int,
    )

    data class UpdateCommand(
        val top: Int?,
        val outer: Int?,
        val pants: Int?,
        val sneakers: Int?,
        val bag: Int?,
        val cap: Int?,
        val socks: Int?,
        val accessory: Int?,
    )

    enum class Category {
        TOP, OUTER, PANTS, SNEAKERS, BAG, CAP, SOCKS, ACCESSORY
    }
}

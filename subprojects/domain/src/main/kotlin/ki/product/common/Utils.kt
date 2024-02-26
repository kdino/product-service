package ki.product.common

import java.util.UUID

object Utils {
    fun generateId(className: String) = "$className-${UUID.randomUUID()}"
}

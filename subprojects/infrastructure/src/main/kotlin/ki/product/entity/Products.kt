package ki.product.entity

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = varchar("id", 127)
    val brandName = varchar("brand_name", 127)
    val top = integer("top")
    val outer = integer("outer")
    val pants = integer("pants")
    val sneakers = integer("sneakers")
    val bag = integer("bag")
    val cap = integer("cap")
    val socks = integer("socks")
    val accessory = integer("accessory")
    val total = integer("total")
    val created = long("created")
    val modified = long("modified").nullable()
    val deleted = long("deleted").nullable()

    override val primaryKey = PrimaryKey(id)
}

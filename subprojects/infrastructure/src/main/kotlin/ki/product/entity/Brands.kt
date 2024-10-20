package ki.product.entity

import org.jetbrains.exposed.sql.Table

object Brands : Table() {
    val id = varchar("id", 127).uniqueIndex()
    val name = varchar("name", 127)
    val created = long("created")
    val modified = long("modified").nullable()
    val deleted = long("deleted").nullable()
}

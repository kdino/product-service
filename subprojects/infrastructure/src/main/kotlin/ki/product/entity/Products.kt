package ki.product.entity

import ki.product.model.Product
import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = varchar("id", 127).uniqueIndex()
    val brandId = varchar("brandId", 127)
    val price = integer("price")
    val category = enumerationByName("category", 31, Product.Category::class)
    val created = long("created")
    val modified = long("modified").nullable()
    val deleted = long("deleted").nullable()

    override val primaryKey = PrimaryKey(id)
}

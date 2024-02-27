package db.migration

import ki.product.common.Utils
import ki.product.entity.Products
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V1__create_products : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Products)

            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "A"
                it[top] = 11200
                it[outer] = 5500
                it[pants] = 4200
                it[sneakers] = 9000
                it[bag] = 2000
                it[cap] = 1700
                it[socks] = 1800
                it[accessory] = 2300
                it[total] = 37700
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "B"
                it[top] = 10500
                it[outer] = 5900
                it[pants] = 3800
                it[sneakers] = 9100
                it[bag] = 2100
                it[cap] = 2000
                it[socks] = 2000
                it[accessory] = 2200
                it[total] = 37600
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "C"
                it[top] = 10000
                it[outer] = 6200
                it[pants] = 3300
                it[sneakers] = 9200
                it[bag] = 2200
                it[cap] = 1900
                it[socks] = 2200
                it[accessory] = 2100
                it[total] = 37100
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "D"
                it[top] = 10100
                it[outer] = 5100
                it[pants] = 3000
                it[sneakers] = 9500
                it[bag] = 2500
                it[cap] = 1500
                it[socks] = 2400
                it[accessory] = 2000
                it[total] = 36100
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "E"
                it[top] = 10700
                it[outer] = 5000
                it[pants] = 3800
                it[sneakers] = 9900
                it[bag] = 2300
                it[cap] = 1800
                it[socks] = 2100
                it[accessory] = 2100
                it[total] = 37700
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "F"
                it[top] = 11200
                it[outer] = 7200
                it[pants] = 4000
                it[sneakers] = 9300
                it[bag] = 2100
                it[cap] = 1600
                it[socks] = 2300
                it[accessory] = 1900
                it[total] = 39600
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "G"
                it[top] = 10500
                it[outer] = 5800
                it[pants] = 3900
                it[sneakers] = 9000
                it[bag] = 2200
                it[cap] = 1700
                it[socks] = 2100
                it[accessory] = 2000
                it[total] = 37200
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "H"
                it[top] = 10800
                it[outer] = 6300
                it[pants] = 3100
                it[sneakers] = 9700
                it[bag] = 2100
                it[cap] = 1600
                it[socks] = 2000
                it[accessory] = 2000
                it[total] = 37600
                it[created] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandName] = "I"
                it[top] = 11400
                it[outer] = 6700
                it[pants] = 3200
                it[sneakers] = 9500
                it[bag] = 2400
                it[cap] = 1700
                it[socks] = 1700
                it[accessory] = 2400
                it[total] = 38900
                it[created] = System.currentTimeMillis()
            }
        }
    }
}

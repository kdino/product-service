package db.migration

import ki.product.common.Utils
import ki.product.entity.Brands
import ki.product.entity.Products
import ki.product.model.Product
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V1__create_products : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Products)
            SchemaUtils.create(Brands)

            val aId = Utils.generateId("brand")
            val bId = Utils.generateId("brand")
            val cId = Utils.generateId("brand")
            val dId = Utils.generateId("brand")
            val eId = Utils.generateId("brand")
            val fId = Utils.generateId("brand")
            val gId = Utils.generateId("brand")
            val hId = Utils.generateId("brand")
            val iId = Utils.generateId("brand")

            Brands.insert {
                it[id] = aId
                it[name] = "A"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = bId
                it[name] = "B"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = cId
                it[name] = "C"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = dId
                it[name] = "D"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = eId
                it[name] = "E"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = fId
                it[name] = "F"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = gId
                it[name] = "G"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = hId
                it[name] = "H"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Brands.insert {
                it[id] = iId
                it[name] = "I"
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand A
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 11200
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 5500
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 4200
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 9000
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 2000
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 1700
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 1800
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = aId
                it[price] = 2300
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand B
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 10500
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 5900
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 3800
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 9100
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 2100
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 2000
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 2000
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = bId
                it[price] = 2200
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand C
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 10000
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 6200
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 3300
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 9200
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 2200
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 1900
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 2200
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = cId
                it[price] = 2100
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand D
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 10100
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 5100
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 3000
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 9500
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 2500
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 1500
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 2400
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = dId
                it[price] = 2000
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand E
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 10700
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 5000
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 3800
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 9900
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 2300
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 1800
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 2100
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = eId
                it[price] = 2100
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand F
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 11200
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 7200
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 4000
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 9300
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 2100
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 1600
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 2300
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = fId
                it[price] = 1900
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand G
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 10500
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 5800
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 3900
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 9000
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 2200
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 1700
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 2100
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = gId
                it[price] = 2000
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand H
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 10800
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 6300
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 3100
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 9700
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 2100
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 1600
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 2000
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = hId
                it[price] = 2000
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }

            // Brand I
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 11400
                it[category] = Product.Category.TOP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 6700
                it[category] = Product.Category.OUTER
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 3200
                it[category] = Product.Category.PANTS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 9500
                it[category] = Product.Category.SNEAKERS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 2400
                it[category] = Product.Category.BAG
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 1700
                it[category] = Product.Category.CAP
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 1700
                it[category] = Product.Category.SOCKS
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
            Products.insert {
                it[id] = Utils.generateId("product")
                it[brandId] = iId
                it[price] = 2400
                it[category] = Product.Category.ACCESSORY
                it[created] = System.currentTimeMillis()
                it[modified] = System.currentTimeMillis()
            }
        }
    }
}

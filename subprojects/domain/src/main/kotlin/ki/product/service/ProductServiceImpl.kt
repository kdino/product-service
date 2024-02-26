package ki.product.service

import arrow.core.raise.Effect

class ProductServiceImpl() : ProductService {
    override fun getCheapestCombination(): Effect<ProductService.Failure, ProductService.CheapestCombinationResult> {
        TODO("Not yet implemented")
    }
}

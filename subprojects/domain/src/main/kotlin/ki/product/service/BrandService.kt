package ki.product.service

import arrow.core.raise.Effect
import ki.product.model.Brand

interface BrandService {
    suspend fun getBrands(): Effect<GetBrandsFailure, List<Brand>>
    suspend fun createBrand(command: CreateBrandCommand): Effect<CreateBrandFailure, Brand>
    suspend fun updateBrand(id: String, name: String): Effect<UpdateBrandFailure, Brand>
    suspend fun deleteBrand(id: String): Effect<DeleteBrandFailure, Unit>

    sealed interface GetBrandsFailure
    sealed interface CreateBrandFailure
    sealed interface UpdateBrandFailure
    sealed interface DeleteBrandFailure

    data object BrandNotFound : GetBrandsFailure, UpdateBrandFailure, DeleteBrandFailure
    data class InternalError(val message: String?) : GetBrandsFailure, CreateBrandFailure, UpdateBrandFailure, DeleteBrandFailure
    data object BrandNameAlreadyExists : CreateBrandFailure, UpdateBrandFailure

    data class CreateBrandCommand(
        val name: String,
        val top: Int,
        val outer: Int,
        val pants: Int,
        val sneakers: Int,
        val bag: Int,
        val cap: Int,
        val socks: Int,
        val accessory: Int,
    )
}

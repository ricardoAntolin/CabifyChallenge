package dev.ricardoantolin.cabifystore.data.providers.remote

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import io.reactivex.Single

interface ProductsRemoteProvider {
    fun fetchProducts(): Single<List<ProductEntity>>
}
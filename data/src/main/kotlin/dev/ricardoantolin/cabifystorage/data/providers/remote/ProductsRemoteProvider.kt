package dev.ricardoantolin.cabifystorage.data.providers.remote

import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import io.reactivex.Single

interface ProductsRemoteProvider {
    fun fetchProducts(): Single<List<ProductEntity>>
}
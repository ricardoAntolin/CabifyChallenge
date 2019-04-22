package dev.ricardoantolin.cabifystorage.remote.services.products

import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import dev.ricardoantolin.cabifystorage.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystorage.remote.APIService
import dev.ricardoantolin.cabifystorage.remote.mappers.asDataEntity
import io.reactivex.Single

class RemoteProductsProvider constructor(baseURL: String, debug: Boolean):
    APIService<APIProductsService>(APIProductsService::class.java, baseURL, debug), ProductsRemoteProvider {

    override fun fetchProducts(): Single<List<ProductEntity>>
            = service.fetchProducts().asDataEntity()
}
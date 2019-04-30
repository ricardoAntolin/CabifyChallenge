package dev.ricardoantolin.cabifystore.remote.services.products

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystore.remote.APIService
import dev.ricardoantolin.cabifystore.remote.mappers.asDataEntity
import io.reactivex.Single

class RemoteProductsProvider constructor(baseURL: String, debug: Boolean):
    APIService<APIProductsService>(APIProductsService::class.java, baseURL, debug), ProductsRemoteProvider {

    override fun fetchProducts(): Single<List<ProductEntity>>
            = service.fetchProducts().asDataEntity()
}
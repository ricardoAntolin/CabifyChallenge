package dev.ricardoantolin.cabifystore.data.repositories

import dev.ricardoantolin.cabifystore.data.mappers.asDomain
import dev.ricardoantolin.cabifystore.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystore.data.providers.storage.ProductsStorageProvider
import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.domain.repositories.ProductsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class DataProductsRepository @Inject constructor(
    private val storageProvider: ProductsStorageProvider,
    private val remoteProvider: ProductsRemoteProvider
): ProductsRepository {
    override fun updateProducts(): Completable {
        return remoteProvider.fetchProducts()
            .flatMapCompletable { storageProvider.saveAll(it) }
    }

    override fun getProducts(): Flowable<List<Product>> {
        return storageProvider.findAll().asDomain()
    }
}
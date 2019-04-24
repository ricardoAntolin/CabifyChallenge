package dev.ricardoantolin.cabifystorage.data.repositories

import dev.ricardoantolin.cabifystorage.data.mappers.asDomain
import dev.ricardoantolin.cabifystorage.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystorage.data.providers.storage.ProductsStorageProvider
import dev.ricardoantolin.cabifystorage.domain.models.Product
import dev.ricardoantolin.cabifystorage.domain.repositories.ProductsRepository
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
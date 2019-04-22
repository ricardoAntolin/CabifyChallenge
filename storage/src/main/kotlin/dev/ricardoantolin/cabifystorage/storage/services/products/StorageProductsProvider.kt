package dev.ricardoantolin.cabifystorage.storage.services.products

import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import dev.ricardoantolin.cabifystorage.data.providers.storage.ProductsStorageProvider
import dev.ricardoantolin.cabifystorage.storage.entities.RLProductEntity
import dev.ricardoantolin.cabifystorage.storage.mappers.asDataEntity
import dev.ricardoantolin.cabifystorage.storage.mappers.asRealmEntity
import io.reactivex.Flowable
import javax.inject.Inject

class StorageProductsProvider @Inject constructor(private val service: RealmProductsService): ProductsStorageProvider {
    override fun saveAll(products: List<ProductEntity>) = service.save(products.asRealmEntity())
    override fun findAll(): Flowable<List<ProductEntity>> = service.findAll<RLProductEntity>().asDataEntity()
}
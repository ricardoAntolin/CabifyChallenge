package dev.ricardoantolin.cabifystore.storage.services.products

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.data.providers.storage.ProductsStorageProvider
import dev.ricardoantolin.cabifystore.storage.entities.RLProductEntity
import dev.ricardoantolin.cabifystore.storage.mappers.asDataEntity
import dev.ricardoantolin.cabifystore.storage.mappers.asRealmEntity
import io.reactivex.Flowable
import javax.inject.Inject

class StorageProductsProvider @Inject constructor(): ProductsStorageProvider {
    private val service: RealmProductsService = RealmProductsService()
    override fun saveAll(products: List<ProductEntity>) = service.save(products.asRealmEntity())
    override fun findAll(): Flowable<List<ProductEntity>> = service.findAll<RLProductEntity>().asDataEntity()
}
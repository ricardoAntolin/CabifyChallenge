package dev.ricardoantolin.cabifystorage.data.providers.storage

import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ProductsStorageProvider {
    fun saveAll(products: List<ProductEntity>): Completable
    fun findAll(): Flowable<List<ProductEntity>>
}
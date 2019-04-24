package dev.ricardoantolin.cabifystorage.domain.repositories

import dev.ricardoantolin.cabifystorage.domain.models.Product
import io.reactivex.Completable
import io.reactivex.Flowable

interface ProductsRepository {
    fun updateProducts(): Completable
    fun getProducts(): Flowable<List<Product>>
}
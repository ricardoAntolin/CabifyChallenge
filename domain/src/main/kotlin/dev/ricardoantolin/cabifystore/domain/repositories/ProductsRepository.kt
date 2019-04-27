package dev.ricardoantolin.cabifystore.domain.repositories

import dev.ricardoantolin.cabifystore.domain.models.Product
import io.reactivex.Completable
import io.reactivex.Flowable

interface ProductsRepository {
    fun updateProducts(): Completable
    fun getProducts(): Flowable<List<Product>>
}
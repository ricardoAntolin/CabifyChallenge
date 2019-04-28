package dev.ricardoantolin.cabifystore.data.providers.storage

import dev.ricardoantolin.cabifystore.data.entities.CartEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface CartStorageProvider {
    fun save(cart: CartEntity): Completable
    fun findCart(): Flowable<CartEntity>
}
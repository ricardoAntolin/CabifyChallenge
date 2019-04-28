package dev.ricardoantolin.cabifystore.storage.services.cart

import dev.ricardoantolin.cabifystore.data.entities.CartEntity
import dev.ricardoantolin.cabifystore.data.providers.storage.CartStorageProvider
import dev.ricardoantolin.cabifystore.storage.entities.CART_SINGLE_ID
import dev.ricardoantolin.cabifystore.storage.entities.RLCartEntity
import dev.ricardoantolin.cabifystore.storage.mappers.asDataEntity
import dev.ricardoantolin.cabifystore.storage.mappers.asRealmEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class StorageCartProvider @Inject constructor(): CartStorageProvider {
    private val service = RealmCartService()
    override fun save(cart: CartEntity): Completable = service.save(cart.asRealmEntity())

    override fun findCart(): Flowable<CartEntity> =
        service.findByPrimaryKey<RLCartEntity>(CART_SINGLE_ID).asDataEntity()
}
package dev.ricardoantolin.cabifystore.data.repositories

import dev.ricardoantolin.cabifystore.data.mappers.asDataEntity
import dev.ricardoantolin.cabifystore.data.mappers.asDomain
import dev.ricardoantolin.cabifystore.data.providers.storage.CartStorageProvider
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class DataCartRepository @Inject constructor(
    private val storageProvider: CartStorageProvider
): CartRepository {
    override fun saveCart(cart: Cart): Completable {
        return storageProvider.save(cart.asDataEntity())
    }

    override fun getCart(): Flowable<Cart> {
        return storageProvider.findCart().asDomain()
    }
}
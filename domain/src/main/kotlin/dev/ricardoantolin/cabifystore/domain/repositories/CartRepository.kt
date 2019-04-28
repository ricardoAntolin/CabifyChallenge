package dev.ricardoantolin.cabifystore.domain.repositories

import dev.ricardoantolin.cabifystore.domain.models.Cart
import io.reactivex.Completable
import io.reactivex.Flowable

interface CartRepository {
    fun saveCart(cart: Cart): Completable
    fun getCart(): Flowable<Cart>
}
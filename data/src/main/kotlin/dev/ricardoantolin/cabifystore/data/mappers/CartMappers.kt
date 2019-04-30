package dev.ricardoantolin.cabifystore.data.mappers

import dev.ricardoantolin.cabifystore.data.entities.CartEntity
import dev.ricardoantolin.cabifystore.domain.models.Cart
import io.reactivex.Flowable

fun CartEntity.asDomain(): Cart = Cart(products.asDomain())

fun List<CartEntity>.asDomain(): List<Cart> = map { it.asDomain() }

fun Flowable<CartEntity>.asDomain(): Flowable<Cart> = this.map { it.asDomain() }

fun Cart.asDataEntity(): CartEntity = CartEntity(products.asDataEntity())
package dev.ricardoantolin.cabifystore.storage.mappers

import dev.ricardoantolin.cabifystore.data.entities.CartEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLCartEntity
import io.reactivex.Flowable
import io.realm.RealmList

fun RLCartEntity.asDataEntity(): CartEntity = CartEntity(products.asDataEntity())

fun Flowable<RLCartEntity>.asDataEntity(): Flowable<CartEntity> = this.map { it.asDataEntity() }

fun CartEntity.asRealmEntity(): RLCartEntity =
    RLCartEntity(products = RealmList(*products.asRealmEntity().toTypedArray()))

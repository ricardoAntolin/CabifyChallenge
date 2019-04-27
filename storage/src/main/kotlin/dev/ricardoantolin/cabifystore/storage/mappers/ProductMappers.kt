package dev.ricardoantolin.cabifystore.storage.mappers

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLProductEntity
import io.reactivex.Flowable

fun RLProductEntity.asDataEntity(): ProductEntity = ProductEntity(code, name, price)

fun List<RLProductEntity>.asDataEntity(): List<ProductEntity> = map { it.asDataEntity() }

fun Flowable<List<RLProductEntity>>.asDataEntity() = this.map { it.asDataEntity() }

fun ProductEntity.asRealmEntity(): RLProductEntity = RLProductEntity(code, name, price)

fun List<ProductEntity>.asRealmEntity(): List<RLProductEntity> = map { it.asRealmEntity() }

package dev.ricardoantolin.cabifystorage.remote.mappers

import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import dev.ricardoantolin.cabifystorage.remote.entities.RMProductEntity
import dev.ricardoantolin.cabifystorage.remote.responses.ProductsResponse
import io.reactivex.Single

fun RMProductEntity.asDataEntity(): ProductEntity = ProductEntity(code, name, price)

fun ProductsResponse.asDataEntity(): List<ProductEntity> = products.map { it.asDataEntity() }

fun Single<ProductsResponse>.asDataEntity() = this.map { it.asDataEntity() }
package dev.ricardoantolin.cabifystorage.remote.mappers

import dev.ricardoantolin.cabifystorage.data.entities.ProductsEntity
import dev.ricardoantolin.cabifystorage.remote.models.RMProductEntity
import dev.ricardoantolin.cabifystorage.remote.responses.ProductsResponse
import io.reactivex.Single

fun RMProductEntity.asDataEntity(): ProductsEntity = ProductsEntity(code, name, price)

fun ProductsResponse.asDataEntity(): List<ProductsEntity> = products.map { it.asDataEntity() }

fun Single<ProductsResponse>.asDataEntity() = this.map { it.asDataEntity() }
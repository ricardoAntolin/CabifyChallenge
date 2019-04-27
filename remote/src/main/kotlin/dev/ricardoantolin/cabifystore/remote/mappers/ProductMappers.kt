package dev.ricardoantolin.cabifystore.remote.mappers

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.remote.entities.RMProductEntity
import dev.ricardoantolin.cabifystore.remote.responses.ProductsResponse
import io.reactivex.Single

fun RMProductEntity.asDataEntity(): ProductEntity = ProductEntity(code, name, price)

fun ProductsResponse.asDataEntity(): List<ProductEntity> = products.map { it.asDataEntity() }

fun Single<ProductsResponse>.asDataEntity() = this.map { it.asDataEntity() }
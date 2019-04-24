package dev.ricardoantolin.cabifystorage.data.mappers

import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import dev.ricardoantolin.cabifystorage.domain.models.Product
import io.reactivex.Flowable

fun ProductEntity.asDomain(): Product = Product(code, name, price)

fun List<ProductEntity>.asDomain(): List<Product> = map { it.asDomain() }

fun Flowable<List<ProductEntity>>.asDomain() = this.map { it.asDomain() }
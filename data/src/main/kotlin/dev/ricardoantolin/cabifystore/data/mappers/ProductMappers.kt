package dev.ricardoantolin.cabifystore.data.mappers

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.domain.models.Product
import io.reactivex.Flowable

fun ProductEntity.asDomain(): Product = Product(code, name, price)

fun List<ProductEntity>.asDomain(): List<Product> = map { it.asDomain() }

fun Flowable<List<ProductEntity>>.asDomain() = this.map { it.asDomain() }
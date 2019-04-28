package dev.ricardoantolin.cabifystore.mappers

import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.Flowable

fun UIProduct.asDomain(): Product = Product(code, name, price)

fun List<UIProduct>.asDomain(): List<Product> = map { it.asDomain() }

fun Flowable<List<UIProduct>>.asDomain(): Flowable<List<Product>> = this.map { it.asDomain() }

fun Product.asUIModel(): UIProduct = UIProduct(code, name, price)

fun List<Product>.asUIModel(): List<UIProduct> = map { it.asUIModel() }

fun Flowable<List<Product>>.asUIModel(): Flowable<List<UIProduct>> = this.map { it.asUIModel() }
package dev.ricardoantolin.cabifystore.mappers

import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.models.Discount
import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.models.CartItem
import dev.ricardoantolin.cabifystore.models.UICart
import dev.ricardoantolin.cabifystore.models.UIDiscount
import io.reactivex.Flowable

fun Cart.asUIModel(): UICart = UICart(
    cartId = cartId,
    products = products.groupBy(Product::code).map { CartItem(it.value.first().asUIModel(), it.value.count() ) },
    discounts = discounts.asUIModel())

fun Flowable<Cart>.asUIModel(): Flowable<UICart> = this.map { it.asUIModel() }

fun Discount.asUIModel(): UIDiscount = UIDiscount(code, quantity)

fun List<Discount>.asUIModel(): List<UIDiscount> = this.map { it.asUIModel() }
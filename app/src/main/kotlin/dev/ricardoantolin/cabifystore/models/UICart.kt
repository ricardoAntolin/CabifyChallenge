package dev.ricardoantolin.cabifystore.models

class UICart (
    val products: List<CartItem>,
    val discounts: List<UIDiscount>
){
    val subTotal: Float = products
        .map { it.quantity * it.product.price }
        .takeIf { it.isNotEmpty() }
        ?.reduce { total, cartItem -> total + cartItem }
        ?: 0f

    val totalDiscount: Float = discounts
        .map { it.quantity }
        .takeIf { it.isNotEmpty() }
        ?.reduce { total, discount -> total + discount }
        ?: 0f

    val total: Float = subTotal + totalDiscount
}


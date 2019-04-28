package dev.ricardoantolin.cabifystore.models

import java.util.*

class UICart (
    val cartId: UUID?,
    val products: List<CartItem>,
    val discounts: List<UIDiscount>
)


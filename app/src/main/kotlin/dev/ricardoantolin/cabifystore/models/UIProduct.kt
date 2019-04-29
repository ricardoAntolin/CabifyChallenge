package dev.ricardoantolin.cabifystore.models

import dev.ricardoantolin.cabifystore.R

data class UIProduct(
    var code: String,
    var name: String,
    var price: Float
) {
    val image: Int? = when (code) {
        "VOUCHER" -> R.drawable.cabify_voucher
        "TSHIRT" -> R.drawable.cabify_tshirt
        "MUG" -> R.drawable.cabify_mug
        else -> null
    }
}
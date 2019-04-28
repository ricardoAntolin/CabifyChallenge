package dev.ricardoantolin.cabifystore.domain.models

const val TSHIRT_DISCOUNT_CODE = "tshirt.discount"
const val VOUCHER_DISCOUNT_CODE = "voucher.discount"

data class Cart(
    val products: List<Product>
) {
    val discounts: List<Discount> = calculateDiscounts()

    private fun calculateDiscounts(): List<Discount> {
        val discounts = mutableListOf<Discount>()
        calculateVoucherDiscount()?.let { discounts.add(it) }
        calculateTShirtDiscount()?.let { discounts.add(it) }
        return discounts
    }

    private fun calculateVoucherDiscount(): Discount? {
        val voucherCode = "VOUCHER"
        val offerDiscount = -5f
        return products.filter { it.code == voucherCode }
            .takeIf { (it.count() / 2) > 0 }
            ?.let { Discount(VOUCHER_DISCOUNT_CODE, (it.count() / 2) * offerDiscount) }
    }

    private fun calculateTShirtDiscount(): Discount? {
        val tshirtCode = "TSHIRT"
        val offerDiscount = -1f
        return products.filter { it.code == tshirtCode }
            .takeIf { it.count() > 3 }
            ?.let { Discount(TSHIRT_DISCOUNT_CODE, it.count() * offerDiscount) }
    }
}


data class Discount(
    val code: String,
    val quantity: Float
)
package dev.ricardoantolin.cabifystore.domain.models

import org.junit.Test

import org.junit.Assert.*

class CartTest {
    @Test
    fun should_not_get_any_discounts() {
        val cart = Cart(
            listOf(
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f)
            )
        )

        assert(cart.discounts.count() == 0)
    }

    @Test
    fun should_get_tshirt_discounts_only() {
        val cart = Cart(
            listOf(
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f)
            )
        )

        assert(cart.discounts.count() == 1)
        assert(cart.discounts.first().code == "tshirt.discount")
        assert(cart.discounts.first().quantity == -4f)
    }

    @Test
    fun should_get_voucher_discounts_only() {
        val cart = Cart(
            listOf(
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("TSHIRT", "Cabify T-Shirt", 20f)
            )
        )

        assert(cart.discounts.count() == 1)
        assert(cart.discounts.first().code == "voucher.discount")
        assert(cart.discounts.first().quantity == -10f)
    }

    @Test
    fun should_get_voucher_and_tshirt_discounts() {
        val cart = Cart(
            listOf(
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("MUG", "Cabify Coffee Mug", 7.5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("TSHIRT", "Cabify T-Shirt", 20f)
            )
        )

        assert(cart.discounts.count() == 2)
        assert(cart.discounts.filter { it.code == "voucher.discount"}.count() == 1)
        assert(cart.discounts.filter { it.code == "tshirt.discount"}.count() == 1)
        assert(cart.discounts.first { it.code == "tshirt.discount"}.quantity == -4f)
        assert(cart.discounts.first { it.code == "voucher.discount"}.quantity == -10f)
    }
}
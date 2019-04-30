package dev.ricardoantolin.cabifystore.storage.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

const val CART_SINGLE_ID = "cart.id"
open class RLCartEntity(
    @PrimaryKey
    var cartId: String = CART_SINGLE_ID,
    var products: RealmList<RLProductEntity> = RealmList()
): RealmObject(){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RLCartEntity) return false

        if (cartId != other.cartId) return false
        if (products != other.products) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cartId.hashCode()
        result = 31 * result + products.hashCode()
        return result
    }
}
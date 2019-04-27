package dev.ricardoantolin.cabifystore.storage.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RLProductEntity(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var price: Float = 0f
): RealmObject() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RLProductEntity) return false

        if (code != other.code) return false
        if (name != other.name) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }

    override fun toString(): String {
        return "RLProductEntity(code='$code', name='$name', price=$price)"
    }
}
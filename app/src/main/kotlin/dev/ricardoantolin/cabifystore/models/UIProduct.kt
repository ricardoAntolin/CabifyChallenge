package dev.ricardoantolin.cabifystore.models

import android.os.Parcel
import android.os.Parcelable
import dev.ricardoantolin.cabifystore.R

data class UIProduct(
    var code: String,
    var name: String,
    var price: Float
) : Parcelable {
    val image: Int? = when (code) {
        "VOUCHER" -> R.drawable.cabify_voucher
        "TSHIRT" -> R.drawable.cabify_tshirt
        "MUG" -> R.drawable.cabify_mug
        else -> null
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeFloat(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UIProduct> {
        override fun createFromParcel(parcel: Parcel): UIProduct {
            return UIProduct(parcel)
        }

        override fun newArray(size: Int): Array<UIProduct?> {
            return arrayOfNulls(size)
        }
    }
}
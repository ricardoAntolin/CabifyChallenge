package dev.ricardoantolin.cabifystore.scenes.cart

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.ricardoantolin.cabifystore.models.CartItem
import dev.ricardoantolin.cabifystore.models.UIProduct
import kotlinx.android.synthetic.main.recycler_item_cart.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk23.listeners.onClick
import kotlin.reflect.KFunction1

class CartViewHolder(
    private val view: View,
    private val removeAllAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>,
    private val addAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>,
    private val removeOneAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>
) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(cartItem: CartItem) = with(view){
        itemImage.image = cartItem.product.image?.let { ContextCompat.getDrawable(context, it) }
        txtItemName.text = cartItem.product.name
        txtItemUnits.text = "${cartItem.quantity} unit x ${cartItem.product.price} €}" //TODO do with string plurals
        txtItemPrice.text = "${cartItem.product.price * cartItem.quantity} €"

        btnRemoveAll.onClick { removeAllAction(cartItem.product) }
        btnAddOne.onClick { addAction(cartItem.product) }
        btnRemoveOne.onClick { removeOneAction(cartItem.product) }
    }
}
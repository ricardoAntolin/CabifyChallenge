package dev.ricardoantolin.cabifystore.scenes.products.list

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.ricardoantolin.cabifystore.models.UIProduct
import kotlinx.android.synthetic.main.recycler_item_product.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk23.listeners.onClick
import kotlin.reflect.KFunction1


class ProductListViewHolder(
    private val view: View,
    private val clickAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>,
    private val addToCartAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>
) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(product: UIProduct) = with(view){
        productImage.image = product.image?.let { ContextCompat.getDrawable(context, it) }
        productTitle.text = product.name
        productPrice.text = "${product.price} €"
        onClick { clickAction(product) }
        btnAddCart.onClick { addToCartAction(product) }
    }
}
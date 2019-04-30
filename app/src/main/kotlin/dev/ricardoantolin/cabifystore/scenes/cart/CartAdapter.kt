package dev.ricardoantolin.cabifystore.scenes.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.extensions.inflate
import dev.ricardoantolin.cabifystore.models.CartItem
import dev.ricardoantolin.cabifystore.models.UIProduct
import kotlin.reflect.KFunction1

class CartAdapter(
    private var items: List<CartItem> = mutableListOf(),
    private val removeAllAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>,
    private val addAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>,
    private val removeOneAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
            parent.inflate(R.layout.recycler_item_cart),
            removeAllAction,
            addAction,
            removeOneAction
        )

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(items: List<CartItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
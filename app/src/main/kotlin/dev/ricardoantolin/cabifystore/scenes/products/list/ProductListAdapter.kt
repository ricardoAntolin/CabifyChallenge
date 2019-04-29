package dev.ricardoantolin.cabifystore.scenes.products.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.extensions.inflate
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KFunction1

class ProductListAdapter(
    private var productList: List<UIProduct> = mutableListOf(),
    private val clickAction: KFunction1<@ParameterName(name = "product") UIProduct, Unit>,
    private val addToCartAction: PublishSubject<UIProduct>
) : RecyclerView.Adapter<ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {

        return ProductListViewHolder(
            parent.inflate(R.layout.recycler_item_product),
            clickAction,
            addToCartAction
        )
    }

    override fun getItemCount(): Int = productList.count()

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    fun setItems(productList: List<UIProduct>) {
        this.productList = productList
        notifyDataSetChanged()
    }
}
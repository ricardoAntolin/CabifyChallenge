package dev.ricardoantolin.cabifystore.scenes.products.list

import android.os.Bundle
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragmentActivity
import dev.ricardoantolin.cabifystore.extensions.navigateToFragment


class ProductListActivity : BaseFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        navigateToFragment(ProductListFragment(),R.id.container,false)
    }
}

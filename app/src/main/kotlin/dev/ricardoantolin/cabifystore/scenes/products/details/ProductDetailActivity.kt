package dev.ricardoantolin.cabifystore.scenes.products.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragmentActivity
import dev.ricardoantolin.cabifystore.extensions.navigateToFragment
import dev.ricardoantolin.cabifystore.scenes.products.list.ProductListActivity
import dev.ricardoantolin.cabifystore.scenes.products.list.ProductListFragment
import kotlinx.android.synthetic.main.activity_product_detail.*

/**
 * An activity representing a single Product detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ProductListActivity].
 */
class ProductDetailActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val fragment = ProductDetailFragment()
        fragment.arguments = intent.extras
        navigateToFragment(fragment,R.id.container,false)
    }
}

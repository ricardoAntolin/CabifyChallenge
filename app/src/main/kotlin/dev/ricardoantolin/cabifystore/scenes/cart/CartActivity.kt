package dev.ricardoantolin.cabifystore.scenes.cart

import android.os.Bundle
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragmentActivity
import dev.ricardoantolin.cabifystore.extensions.navigateToFragment

class CartActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        navigateToFragment(CartFragment(), R.id.container,false)
    }
}

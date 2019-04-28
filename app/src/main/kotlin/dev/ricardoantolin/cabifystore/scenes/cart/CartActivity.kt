package dev.ricardoantolin.cabifystore.scenes.cart

import android.os.Bundle
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragmentActivity

class CartActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    CartFragment.newInstance()
                )
                .commitNow()
        }
    }
}

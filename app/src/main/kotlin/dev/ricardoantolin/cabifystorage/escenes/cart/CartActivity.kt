package dev.ricardoantolin.cabifystorage.escenes.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.ricardoantolin.cabifystorage.R

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)
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

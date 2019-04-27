package dev.ricardoantolin.cabifystore.scenes.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseActivity
import dev.ricardoantolin.cabifystore.common.BaseFragmentActivity
import javax.inject.Inject

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

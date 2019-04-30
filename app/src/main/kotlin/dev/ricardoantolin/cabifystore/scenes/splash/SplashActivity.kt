package dev.ricardoantolin.cabifystore.scenes.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseActivity
import dev.ricardoantolin.cabifystore.extensions.navigateToActivityRemovingPrevious
import dev.ricardoantolin.cabifystore.extensions.setupSnackbar
import dev.ricardoantolin.cabifystore.scenes.products.list.ProductListActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadGif()
        bindViewModel()
    }

    private fun loadGif() {
        Glide.with(this)
            .asGif()
            .load(R.raw.screen_splash)
            .into(splashAnimation)
    }

    private fun bindViewModel(){
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(SplashViewModel::class.java)

        val output = viewModel.transform(SplashViewModel.Input(Observable.just(Unit)))

        output.updateProductsObserver.observe(this, Observer {
            if(it) goToNextActivity()
        })

        content.setupSnackbar(this, output.errorTracker, Snackbar.LENGTH_INDEFINITE, View.OnClickListener {
            goToNextActivity()
        })
    }

    private fun goToNextActivity() {
        navigateToActivityRemovingPrevious(ProductListActivity::class.java)
    }
}
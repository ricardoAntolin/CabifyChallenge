package dev.ricardoantolin.cabifystore.scenes.products.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragment
import dev.ricardoantolin.cabifystore.extensions.inflate
import dev.ricardoantolin.cabifystore.extensions.setupSnackbar
import dev.ricardoantolin.cabifystore.models.UIProduct
import dev.ricardoantolin.cabifystore.scenes.products.list.PRODUCT_INTENT_EXTRA_KEY
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.product_details_description.*
import kotlinx.android.synthetic.main.progress_overlay.*
import org.jetbrains.anko.image

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a [ProductListActivity]
 * in two-pane mode (on tablets) or a [ProductDetailActivity]
 * on handsets.
 */
class ProductDetailFragment : BaseFragment() {
    override lateinit var progressBarOverlay: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_product_detail)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<UIProduct>(PRODUCT_INTENT_EXTRA_KEY)?.let {
            bindProduct(it)
            bindViewModel(it)
        }
        progressBarOverlay = progressbarOverlay
        setUpToolbar()
    }

    @SuppressLint("PrivateResource")
    private fun setUpToolbar() {
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(detailToolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.supportActionBar?.setDisplayShowTitleEnabled(false)
            it.supportActionBar?.setHomeAsUpIndicator(getDrawable(it, R.drawable.abc_ic_ab_back_material))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindProduct(product: UIProduct){
        txtProductName.text = product.name
        txtProductPrice.text = "${product.price} €"
        headerImage.image = product.image?.let { context?.getDrawable(it) }
    }

    private fun bindViewModel(product: UIProduct){
        val viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ProductDetailViewModel::class.java)

        val output = viewModel.transform(
            ProductDetailViewModel.Input(
                Observable.just(product),
                btnAddToCart.clicks().map { product }
            )
        )

        output.updateProductObserver.observe(this, Observer {
            bindProduct(it)
        })

        output.addProductObserver.observe(this, Observer {})

        output.activityTracker.observe(this, Observer {
            trackBackgroudProgress(it)
        })

        content.setupSnackbar(this, output.errorTracker, Snackbar.LENGTH_LONG)
    }
}

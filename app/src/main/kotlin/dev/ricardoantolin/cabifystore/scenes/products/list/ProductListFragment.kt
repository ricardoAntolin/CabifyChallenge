package dev.ricardoantolin.cabifystore.scenes.products.list

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragment
import dev.ricardoantolin.cabifystore.extensions.inflate
import dev.ricardoantolin.cabifystore.extensions.navigateToActivity
import dev.ricardoantolin.cabifystore.extensions.setupSnackbar
import dev.ricardoantolin.cabifystore.models.UIProduct
import dev.ricardoantolin.cabifystore.scenes.cart.CartActivity
import dev.ricardoantolin.cabifystore.scenes.products.details.ProductDetailActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.progress_overlay.*
import org.jetbrains.anko.sdk23.listeners.onClick

const val PRODUCT_INTENT_EXTRA_KEY = "product_intent_extra_key"

class ProductListFragment : BaseFragment() {
    override lateinit var progressBarOverlay: LinearLayout
    private val onAddCartButtonClick = PublishSubject.create<UIProduct>()
    private val adapter =
        ProductListAdapter(clickAction = this::onClickItem, addToCartAction = onAddCartButtonClick)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_product_list)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarOverlay = progressbarOverlay
        setUpToolbar()
        setUpRecycler()
        setListeners()
    }

    private fun setUpToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(appBar)
        appBar.setNavigationOnClickListener(
            NavigationIconClickListener(
                activity!!,
                productGrid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.branded_menu),
                ContextCompat.getDrawable(context!!, R.drawable.close_menu)
            )
        )
    }

    private fun setUpRecycler() {
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = object : GridLayoutManager(
            context,
            2,
            RecyclerView.HORIZONTAL,
            false
        ) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.width = (recyclerView.width * 0.6).toInt()
                return super.checkLayoutParams(lp)
            }
        }
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        val topPadding = resources.getDimensionPixelSize(R.dimen.staggered_product_list_spacing_top)
        val largePadding = resources.getDimensionPixelSize(R.dimen.staggered_product_list_spacing_large)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.staggered_product_list_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(topPadding, largePadding, smallPadding))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            productGrid.background = context?.getDrawable(R.drawable.product_list_background)
        }

    }

    private fun bindViewModel() {
        val viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ProductListViewModel::class.java)

        val output = viewModel.transform(
            ProductListViewModel.Input(
                Observable.just(Unit),
                onAddCartButtonClick
            )
        )

        output.productsObserver.observe(this, Observer {
            adapter.setItems(it)
        })

        output.cartObserver.observe(this, Observer {
            toggleCartIconFullEmpty(it.products.count() > 0)
        })

        output.addProductObserver.observe(this, Observer {})

        output.activityTracker.observe(this, Observer {
            trackBackgroudProgress(it)
        })

        content.setupSnackbar(this, output.errorTracker, Snackbar.LENGTH_LONG)
    }

    private fun setListeners() {
        btnGoToCart.onClick {
            activity?.navigateToActivity(CartActivity::class.java)
        }
    }


    private fun toggleCartIconFullEmpty(isFull: Boolean) = if (isFull) {
        cartBadge.visibility = View.VISIBLE
        changeGoToCartButtonIcon(R.drawable.ic_cart_full)
    } else {
        cartBadge.visibility = View.GONE
        changeGoToCartButtonIcon(R.drawable.ic_cart_empty)
    }

    private fun changeGoToCartButtonIcon(resId: Int) = context?.let {
        btnGoToCart.icon = getDrawable(it, resId)
    }

    private fun onClickItem(product: UIProduct) {
        activity?.navigateToActivity(
            ProductDetailActivity::class.java,
            Bundle().apply { putParcelable(PRODUCT_INTENT_EXTRA_KEY, product) })
    }
}

class ProductGridItemDecoration(
    private val topPadding: Int,
    private val largePadding: Int,
    private val smallPadding: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.top = topPadding
        outRect.left = smallPadding
        outRect.right = largePadding
    }
}